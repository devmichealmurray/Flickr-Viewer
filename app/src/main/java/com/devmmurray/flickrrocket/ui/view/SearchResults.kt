package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.RecyclerFlags
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_search_results.*

/**
 * A simple [Fragment] subclass.
 */
class SearchResults : Fragment() {

    lateinit var baseViewModel: BaseViewModel
    private val searchResultsAdapter = FlickrRocketRecyclerAdapter(arrayListOf(), RecyclerFlags.SEARCH_RESULTS)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        baseViewModel.photos.observe(viewLifecycleOwner, photoListObserver)
        baseViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        baseViewModel.loadError.observe(viewLifecycleOwner, onErrorObserver)
        baseViewModel.refresh()

        searchResultsRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchResultsAdapter
        }
    }


    private val photoListObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            searchResultsAdapter.updatePhotoList(it)
            searchResultsRecycler.visibility = View.VISIBLE
            searchResultsError.visibility = View.GONE
            searchResultsLoading.visibility = View.GONE
        }
    }
    private val loadingObserver = Observer<Boolean> { isLoading ->
        searchResultsLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            searchResultsError.visibility = View.GONE
            searchResultsRecycler.visibility = View.GONE
        }
    }
    private val onErrorObserver = Observer<Boolean> { isError ->
        searchResultsError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            searchResultsRecycler.visibility = View.GONE
            searchResultsLoading.visibility = View.GONE
        }
    }

}
