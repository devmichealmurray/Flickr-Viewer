package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.Photo
import com.devmmurray.flickrrocket.ui.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_flickr_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var positionCounter by Delegates.notNull<Int>()
    private var photoArray = ArrayList<Photo>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        homeViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        homeViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
        homeViewModel.photoPosition.observe(viewLifecycleOwner, photoPositionObserver)
        homeViewModel.refresh()

        mainImageView.setOnClickListener {
            homeViewModel.nextPhoto()
            loadNewPhoto(positionCounter)
            homeLoadingView.visibility = View.GONE
        }
    }

    private val photoPositionObserver = Observer<Int> {
         positionCounter = it
    }

    private val photoListObserver = Observer<ArrayList<Photo>> { list ->
        list?.let {
            photoArray = list
            loadNewPhoto(0)
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
        Picasso.get()
            .load(photoArray[position].link)
            .error(R.drawable.background)
            .placeholder(R.drawable.background)
            .fit()
            .into(mainImageView)
        homeLoadingView.visibility = View.GONE

        imageTitleText.text = photoArray[position].title
    }


}
