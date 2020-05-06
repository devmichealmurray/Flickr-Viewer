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
        homeViewModel.refresh()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Home Fragment", "************* On View Created Called ***************")
        super.onViewCreated(view, savedInstanceState)


//        homeViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
//        homeViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
//        homeViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)

        mainImageView.setOnClickListener {
            homeViewModel.nextPhoto()
            val position = homeViewModel.photoPosition.value
            if (position != null) {
                loadNewPhoto(position)
            }
            homeLoadingView.visibility = View.GONE


        }
        Log.d("OnViewCreated", " ************************* On View Created Ends **********************************")
    }

    override fun onResume() {
        Log.d("OnViewCreated", " ************************* On Resume Called **********************************")

        super.onResume()
        Log.d("OnViewCreated", " ************************* On Resume Ends **********************************")

    }

    override fun onStart() {
        Log.d("OnViewCreated", " ************************* On Start Called **********************************")
        super.onStart()
            loadNewPhoto(0)
        Log.d("OnViewCreated", " ************************* On Start Ends **********************************")
    }

//    private val photoListObserver = Observer<ArrayList<PhotoObject>> {
//            Log.d("Photo List Observer", "*********************** ${args.photoPosition}**********************")
//            loadNewPhoto(3)
//            val position = args.photoPosition
//            if (position > 0) loadNewPhoto(position) else loadNewPhoto(0)
//    }
//
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
        Log.d(
            "Load New Photo",
            "************************** Load New Photo Called ***********************************"
        )
        val photo = homeViewModel.photos.value

        Log.d("Load New Photo", "********************* ${photo?.get(position)?.link}*****************")
        Picasso.get()
            .load(photo?.get(position)?.link)
            .error(R.drawable.background)
            .placeholder(R.drawable.image_placeholder)
            .resize(250, 250)
            .centerInside()
            .into(mainImageView)

        imageTitleText.text = photo?.get(position)?.title
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



