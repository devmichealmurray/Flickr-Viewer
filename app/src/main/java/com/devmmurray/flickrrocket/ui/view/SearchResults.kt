package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.RecyclerFlags
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_search_results.*

class SearchResults : Fragment() {

    lateinit var baseViewModel: BaseViewModel
    private val searchResultsAdapter = FlickrRocketRecyclerAdapter(arrayListOf(), RecyclerFlags.SEARCH_RESULTS)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        baseViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        baseViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
        baseViewModel.refresh(false)

        searchResultsRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchResultsAdapter
        }

        backArrow.setOnClickListener {
            val directions = SearchResultsDirections.actionSearchResultsToSearch()
            Navigation.findNavController(backArrow).navigate(directions)
        }
    }


    /**
     * Observer Values for Live Data
     */

    private val photoListObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            searchResultsAdapter.updatePhotoList(it)
            searchResultsRecycler.visibility = View.VISIBLE
            searchResultsError.visibility = View.GONE
            searchResultsLoading.visibility = View.GONE
        }
    }
    private val loadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            searchResultsLoading.visibility = View.VISIBLE
            searchResultsError.visibility = View.GONE
            searchResultsRecycler.visibility = View.GONE
        }
    }
    private val onErrorObserver = Observer<Boolean> { isError ->
        if (isError) {
            searchResultsError.visibility = View.VISIBLE
            searchResultsRecycler.visibility = View.GONE
            searchResultsLoading.visibility = View.GONE
        }
    }

}
