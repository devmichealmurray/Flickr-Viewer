package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.RecyclerFlags
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_flickr_list.*


class SearchListFragment : Fragment() {

    private val searchListViewModel: BaseViewModel by viewModels()
    private val searchListAdapter = FlickrRocketRecyclerAdapter(arrayListOf(), RecyclerFlags.SEARCH)
    private val suggestionListAdapter = FlickrRocketRecyclerAdapter(
        arrayListOf(
            "Recent",
            "Travel",
            "Decor",
            "Food",
            "Architecture",
            "Art",
            "Nature",
            "Style",
            "Music",
            "Movies",
            "Beauty"
        ), RecyclerFlags.SUGGESTIONS
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flickr_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Live Data Observers
        searchListViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        searchListViewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        searchListViewModel.loadError.observe(viewLifecycleOwner, errorLiveDataObserver)

        searchListViewModel.refresh(false)

        // Adapters for the suggestion and the search recyclerViews
        suggestionsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = suggestionListAdapter
        }

        searchFragRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchListAdapter
        }

        // Functionality for swipe refresh
        refreshLayout.setOnRefreshListener {
            searchFragRecyclerView.visibility = View.VISIBLE
            searchListError.visibility = View.GONE
            searchLoadingView.visibility = View.VISIBLE
            searchListViewModel.refresh(false)
            refreshLayout.isRefreshing = false
        }
    }

    /**
     *  Observer Values for the Live Data
     */

    private val photoListObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            searchListAdapter.updatePhotoList(it)
            searchFragRecyclerView.visibility = View.VISIBLE
            searchListError.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            searchLoadingView.visibility = View.VISIBLE
            searchListError.visibility = View.GONE
            searchFragRecyclerView.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        if (isError) {
            searchListError.visibility = View.VISIBLE
            searchFragRecyclerView.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }

}
