package com.devmmurray.flickrrocket.ui.view

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
            homeViewModel.nextPhoto()
            val position = homeViewModel.photoPosition.value
            if (position != null) {
                loadNewPhoto(position)
            }
            homeLoadingView.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        val isFavorites = args.isFavorites
        homeViewModel.refresh(isFavorites)
    }

    private val photoListObserver = Observer<ArrayList<PhotoObject>> {

        // receivedPosition establishes current photo position sent from navArgs
        val receivedPosition = if (args.photoPosition == 0) 0 else args.photoPosition
        homeViewModel.positionUpdate(receivedPosition)
        it?.let {
            Log.d("Photo List Observer", "************** Size = ${it.size} *****************")
            Log.d("Photo List Observer", "************** Position = $receivedPosition *****************")

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
        Log.d(
            "HomeFragment",
            "*********** Load New Photo Called, title = ${photo?.title}************"
        )
        if (!photo?.isFavorite!!) {
            favorite.setImageResource(R.drawable.ic_favorite_white)
        } else {
            favorite.setImageResource(R.drawable.ic_favorite_red)
        }

        Picasso.get()
            .load(photos[position].link)
            .error(R.drawable.background)
            .placeholder(R.drawable.image_placeholder)
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
    }


}



