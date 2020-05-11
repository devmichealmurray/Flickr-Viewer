package com.devmmurray.flickrrocket.ui.view

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_flickr_list.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NavArg from favorites recycler click, informs viewModel to switch photos list
        // the favorites list from the database
        val isFavorites = args.isFavorites

        homeViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        homeViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        homeViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
        homeViewModel.saved.observe(viewLifecycleOwner, savedObserver)
        homeViewModel.saved.observe(viewLifecycleOwner, removedObserver)
        homeViewModel.refresh(isFavorites)

        mainImageView.setOnClickListener {
            if (commentCardView.visibility == View.GONE) {
                homeViewModel.nextPhoto()
                val position = homeViewModel.photoPosition.value
                if (position != null) {
                    loadNewPhoto(position)
                }
                homeLoadingView.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val isFavorites = args.isFavorites
        homeViewModel.refresh(isFavorites)
    }

    private val photoListObserver = Observer<ArrayList<PhotoObject>> {

        // receivedPosition establishes current photo position sent from navArgs. Args.photoPosition
        //  is sent from the Recycler Adapter and default is 0
        val receivedPosition = args.photoPosition
        homeViewModel.positionUpdate(receivedPosition)
        it?.let {
            if (it.size >= receivedPosition + 1) {
                loadNewPhoto(receivedPosition)
            }
        }
    }

    private val loadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            homeError.visibility = View.GONE
            homeLoadingView.visibility = View.VISIBLE
        }
    }

    private val onErrorObserver = Observer<Boolean> { isError ->
        homeError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            mainImageView.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }

    private val savedObserver = Observer<Boolean> { saved ->
        if (saved) {
            Toast.makeText(context, "Photo Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private val removedObserver = Observer<Boolean> { saved ->
        if (saved) {
            Toast.makeText(context, "Photo Deleted!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadNewPhoto(position: Int) {
        val photos = homeViewModel.photos.value
        val photo = photos?.get(position)

        if (!photo?.isFavorite!!) {
            favorite.setImageResource(R.drawable.ic_favorite_white)
        } else {
            favorite.setImageResource(R.drawable.ic_favorite_red)
        }

        if (photo.comment != "") {
            photoComments.visibility = View.VISIBLE
            photoComments.text = photo.comment
            comment.setImageResource(R.drawable.ic_comment_blue)
        } else {
            photoComments.visibility = View.GONE
            comment.setImageResource(R.drawable.ic_comment_black_24dp)
        }

        Picasso.get()
            .load(photos[position].link)
            .error(R.drawable.background)
            .placeholder(R.drawable.background)
            .resize(250, 250)
            .centerInside()
            .into(mainImageView)

        imageTitleText.text = photos[position].title
        homeLoadingView.visibility = View.GONE

        favorite.setOnClickListener {
            favoriteClickListener(position)
        }

        comment.setOnClickListener {
            commentClickListener(position)
        }

        commentSave.setOnClickListener {
            commentSaveClickListener(position)
        }

        commentCancel.setOnClickListener {
            commentCancelClickListener()
        }

        share.setOnClickListener {
            shareClickListener(position)
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    /**
     *  Click Listener Functions for Favorite, Share, and Commenting
     */

    private fun favoriteClickListener(position: Int) {
        val currentPhoto = position.let { homeViewModel.photos.value?.get(it) }

        if (currentPhoto != null) {
            if (currentPhoto.isFavorite) {
                favorite.setImageResource(R.drawable.ic_favorite_white)
                currentPhoto.isFavorite = false
                homeViewModel.remove(currentPhoto)
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_red)
                currentPhoto.isFavorite = true
                homeViewModel.save(currentPhoto)
            }
        }
        homeLoadingView.visibility = View.GONE
    }

    private fun shareClickListener(position: Int) {
        val photo = position.let { homeViewModel.photos.value?.get(it) }
        val url = photo?.link
        url?.let {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Look at this photo I found with FlickrRocket! \n $url"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context?.startActivity(shareIntent)
        }
    }


    private fun commentClickListener(position: Int) {
        val photo = position.let { homeViewModel.photos.value?.get(it) }
        comment.setImageResource(R.drawable.ic_comment_blue)
        share.visibility = View.GONE
        favorite.visibility = View.GONE
        imageTitleText.visibility = View.GONE
        commentCardView.visibility = View.VISIBLE
        title.text = photo?.title
    }

    private fun commentSaveClickListener(position: Int) {
        val photo = position.let { homeViewModel.photos.value?.get(it) }
        if (photo != null) {
            photo.comment = commentsEditText.text.toString()
            photo.isFavorite = true
            homeViewModel.save(photo)
        }
        commentCardView.visibility = View.GONE
        comment.setImageResource(R.drawable.ic_comment_black_24dp)
        share.visibility = View.VISIBLE
        favorite.visibility = View.VISIBLE
        imageTitleText.visibility = View.VISIBLE
        photoComments.visibility = View.VISIBLE
        photoComments.text = commentsEditText.text.toString()
        commentsEditText.setText("")
        hideKeyboard()
    }

    private fun commentCancelClickListener() {
        commentCardView.visibility = View.GONE
        comment.setImageResource(R.drawable.ic_comment_black_24dp)
        share.visibility = View.VISIBLE
        favorite.visibility = View.VISIBLE
        imageTitleText.visibility = View.VISIBLE
        hideKeyboard()
    }


}



