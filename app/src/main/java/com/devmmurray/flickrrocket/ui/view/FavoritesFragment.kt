package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.adapter.FlickrRocketRecyclerAdapter
import com.devmmurray.flickrrocket.ui.adapter.RecyclerFlags
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_search_results.backArrow

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: BaseViewModel
    private val favoritesListAdapter =
        FlickrRocketRecyclerAdapter(arrayListOf(), RecyclerFlags.FAVORITES)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesViewModel.photos.observe(viewLifecycleOwner, listObserver)
        favoritesViewModel.refresh(true)

        backArrow.setOnClickListener {
            val directions = FavoritesFragmentDirections.actionFavoritesToDetail()
            Navigation.findNavController(backArrow).navigate(directions)
        }

        favoritesRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
            adapter = favoritesListAdapter
        }

        favoriteSwipeLayout.setOnRefreshListener {
            favoritesRecycler.visibility = View.VISIBLE
//            searchListError.visibility = View.GONE
//            searchLoadingView.visibility = View.VISIBLE
            favoritesViewModel.refresh(true)
            favoriteSwipeLayout.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        favoritesViewModel.refresh(true)
    }

    private val listObserver = Observer<ArrayList<PhotoObject>> { list ->
        list?.let {
            favoritesListAdapter.updatePhotoList(it)
            favoritesRecycler.visibility = View.VISIBLE
            favoritesError.visibility = View.GONE
            favoritesLoading.visibility = View.GONE
        }

    }

}
