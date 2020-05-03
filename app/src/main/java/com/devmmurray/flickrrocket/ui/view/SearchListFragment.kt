package com.devmmurray.flickrrocket.ui.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.SuggestionsRecyclerView
import com.devmmurray.flickrrocket.ui.viewmodel.SearchListViewModel
import kotlinx.android.synthetic.main.fragment_flickr_list.*


class SearchListFragment : Fragment(){

    private var searchView: SearchView? = null
    private lateinit var searchListViewModel: SearchListViewModel
    private val searchListAdapter = FlickrRocketRecyclerAdapter(arrayListOf())
    private val suggestionListAdapter = SuggestionsRecyclerView(arrayListOf(
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
    ))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flickr_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchListViewModel = ViewModelProvider(this).get(SearchListViewModel::class.java)

        searchListViewModel.photos.observe(viewLifecycleOwner, photoListDataObserver)
        searchListViewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        searchListViewModel.loadError.observe(viewLifecycleOwner,errorLiveDataObserver)

        searchListViewModel.refresh()

        suggestionsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = suggestionListAdapter
        }

        searchFragRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchListAdapter
        }

        refreshLayout.setOnRefreshListener {
            searchFragRecyclerView.visibility = View.GONE
            searchListError.visibility = View.GONE
            searchLoadingView.visibility = View.VISIBLE

            searchListViewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        val searchManager =
            activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = view.findViewById(R.id.searchView) as SearchView
        val componentName = activity?.componentName
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        Log.d("SearchListFragment", "SearchManager $componentName")
        Log.d("SearchListFragment", "SearchManager Hint: ${searchView?.queryHint}")
        Log.d("SearchListFragment", "SearchManager $searchableInfo")

    }

    private val photoListDataObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            searchListAdapter.updatePhotoList(it)
            searchFragRecyclerView.visibility = View.VISIBLE
            searchListError.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }
    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        searchLoadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            searchListError.visibility = View.GONE
            searchFragRecyclerView.visibility = View.GONE
        }
    }
    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        searchListError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            searchFragRecyclerView.visibility = View.GONE
            searchLoadingView.visibility = View.GONE
        }
    }
}
