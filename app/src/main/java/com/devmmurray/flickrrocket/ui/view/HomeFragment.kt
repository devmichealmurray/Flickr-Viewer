package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.ui.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
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
//
//        baseViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
//        baseViewModel.refresh()

loadNewPhoto(0)

        loadNewPhoto(0)
        mainImageView.setOnClickListener {
            homeViewModel.nextPhoto()
            val position = homeViewModel.photoPosition.value
            if (position != null) {
                loadNewPhoto(position)
            }
            homeLoadingView.visibility = View.GONE
        }
    }

//    override fun onPause() {
//        Log.d("OnResume", "********************* onStart Called*****************")
//        super.onPause()
//        baseViewModel.refresh()
//    }



//    private val photoListObserver = Observer<ArrayList<PhotoObject>> {
//        Log.d("Photo List Observer", "*********************** ${it.size}**********************")
//        it?.let {
//            val photoPosition = args.photoPosition
//            if (photoPosition > 0) {
//                loadNewPhoto(photoPosition)
//            } else {
//                loadNewPhoto(0)
//            }
//            onStart()
//        }
//    }


//    private val loadingObserver = Observer<Boolean> { isLoading ->
//        if (isLoading) {
//            homeError.visibility = View.GONE
//            homeLoadingView.visibility = View.VISIBLE
//        }
//    }
//
//    private val onErrorObserver = Observer<Boolean> { isError ->
//        homeError.visibility = if (isError) View.VISIBLE else View.GONE
//        if (isError) {
//            mainImageView.visibility = View.GONE
//            searchLoadingView.visibility = View.GONE
//        }
//    }

    private fun loadNewPhoto(position: Int) {

        val photos = homeViewModel.photos.value
        Log.d("loadNewPhoto", "************ ${photos?.size}")

        Picasso.get()
            .load(photos?.get(position)?.link)
            .error(R.drawable.background)
            .placeholder(R.drawable.image_placeholder)
            .resize(250, 250)
            .centerInside()
            .into(mainImageView)

        imageTitleText.text = photos?.get(position)?.title
        homeLoadingView.visibility = View.GONE

//        favorite.setOnClickListener {
//            if (photo?.isFavorite) {
//                favorite.setImageResource(R.drawable.ic_favorite_white)
//                // remove from database
//            } else {
//                favorite.setImageResource(R.drawable.ic_favorite_red)
//                // add to photoObject to database
//            }
    }


}



