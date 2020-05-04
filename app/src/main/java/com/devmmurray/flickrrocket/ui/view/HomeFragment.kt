package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_flickr_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: BaseViewModel
    private var positionCounter by Delegates.notNull<Int>()
    private var photoArray = ArrayList<PhotoObject>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        homeViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        homeViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        homeViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
        homeViewModel.photoPosition.observe(viewLifecycleOwner, photoPositionObserver)
        homeViewModel.refresh()

        mainImageView.setOnClickListener {
            if (photoArray.isNotEmpty()) {
            homeViewModel.nextPhoto()
            loadNewPhoto(positionCounter)
            homeLoadingView.visibility = View.GONE
            } else {
                Toast.makeText(context, "No Photos Currently Available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val photoPositionObserver = Observer<Int> {
         positionCounter = it
    }

    private val photoListObserver = Observer<ArrayList<PhotoObject>> { list ->
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

        if(photoArray[position].isFavorite) {
            favorite.setImageResource(R.drawable.ic_favorite_red)
        } else {
            favorite.setImageResource(R.drawable.ic_favorite_white)
        }

        Picasso.get()
            .load(photoArray[position].link)
            .error(R.drawable.background)
            .placeholder(R.drawable.background)
            .resize(250, 250)
            .centerInside()
            .into(mainImageView)

        imageTitleText.text = photoArray[position].title
        homeLoadingView.visibility = View.GONE

        favorite.setOnClickListener {
            if (photoArray[position].isFavorite) {
                favorite.setImageResource(R.drawable.ic_favorite_white)
                // remove from database
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_red)
                // add to photoObject to database
            }
        }

        comment.setOnClickListener {
            // call fragment to add comments
            // add to photoObject database
            // display comments in comment textView
        }
    }


}
