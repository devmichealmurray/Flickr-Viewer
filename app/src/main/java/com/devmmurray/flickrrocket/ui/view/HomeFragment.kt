package com.devmmurray.flickrrocket.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        // the favorites list
        val isFavorites = args.isFavorites

        homeViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        homeViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        homeViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
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
            Log.d("Photo List Observer", "************** Size = ${it.size} *****************")
            Log.d(
                "Photo List Observer",
                "************** Position = $receivedPosition *****************"
            )

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
            if (photo.isFavorite) {
                favorite.setImageResource(R.drawable.ic_favorite_white)
                photo.isFavorite = false
                homeViewModel.remove(photo)
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_red)
                photo.isFavorite = true
                homeViewModel.save(photo)
            }
            homeLoadingView.visibility = View.GONE
        }

        comment.setOnClickListener {
            comment.setImageResource(R.drawable.ic_comment_blue)
            share.visibility = View.GONE
            favorite.visibility = View.GONE
            imageTitleText.visibility = View.GONE
            commentCardView.visibility = View.VISIBLE
            title.text = photos[position].title
        }

        commentSave.setOnClickListener {
            photos[position].comment = commentsEditText.text.toString()
            photo.isFavorite = true
            homeViewModel.save(photo)
            commentCardView.visibility = View.GONE
            comment.setImageResource(R.drawable.ic_comment_black_24dp)
            share.visibility = View.VISIBLE
            favorite.visibility = View.VISIBLE
            imageTitleText.visibility = View.VISIBLE
            photoComments.visibility = View.VISIBLE
            photoComments.text = commentsEditText.text.toString()
            commentsEditText.setText("")
        }

        commentCancel.setOnClickListener {
            commentCardView.visibility = View.GONE
            comment.setImageResource(R.drawable.ic_comment_black_24dp)
            share.visibility = View.VISIBLE
            favorite.visibility = View.VISIBLE
            imageTitleText.visibility = View.VISIBLE
        }

        share.setOnClickListener {
            val url = photos[position].link
            url?.let {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Look at this photo I found with FlickrRocket! \n $url")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context?.startActivity(shareIntent)
            }
        }
    }




}



