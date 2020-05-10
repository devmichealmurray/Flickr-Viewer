package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.RecyclerFlags
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_flickr_list.*


class SearchListFragment : Fragment() {

    private lateinit var searchListViewModel: BaseViewModel
    private val searchListAdapter = FlickrRocketRecyclerAdapter(arrayListOf(), RecyclerFlags.SEARCH)
    private val suggestionListAdapter = FlickrRocketRecyclerAdapter(arrayListOf(
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
    ), RecyclerFlags.SUGGESTIONS)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flickr_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchListViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        searchListViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
//        searchListViewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
//        searchListViewModel.loadError.observe(viewLifecycleOwner,errorLiveDataObserver)

        searchListViewModel.refresh(false)

        suggestionsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = suggestionListAdapter
        }

        searchFragRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchListAdapter
        }

        refreshLayout.setOnRefreshListener {
            searchFragRecyclerView.visibility = View.VISIBLE
//            searchListError.visibility = View.GONE
//            searchLoadingView.visibility = View.VISIBLE
            searchListViewModel.refresh(false)
            refreshLayout.isRefreshing = false
        }
    }

    private val photoListObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            searchListAdapter.updatePhotoList(it)
            searchFragRecyclerView.visibility = View.VISIBLE
            searchListError.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }
//    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
//        searchLoadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
//        if (isLoading) {
//            searchListError.visibility = View.GONE
//            searchFragRecyclerView.visibility = View.GONE
//        }
//    }
//    private val errorLiveDataObserver = Observer<Boolean> { isError ->
//        searchListError.visibility = if (isError) View.VISIBLE else View.GONE
//        if (isError) {
//            searchFragRecyclerView.visibility = View.GONE
//            searchLoadingView.visibility = View.GONE
//        }
//    }

}
