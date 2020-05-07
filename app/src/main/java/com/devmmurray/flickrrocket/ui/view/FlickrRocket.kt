package com.devmmurray.flickrrocket.ui.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.data.model.UrlAddress
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class FlickrRocket : AppCompatActivity() {

    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var navController: NavController
    private var searchView: SearchView? = null
    private lateinit var baseViewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        baseViewModel.photos.observe(this, photoListObserver)
        baseViewModel.refresh()

        setUpNavigation()

    }

    private val photoListObserver = Observer<ArrayList<PhotoObject>> {
        it?.let {

        }
    }

    private fun setUpNavigation() {
        bottomNavBar = findViewById(R.id.bottom_nav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavBar,
            navHostFragment!!.navController
        )
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search ->
                    toolBar.visibility = View.VISIBLE
                else ->
                    toolBar.visibility = View.GONE
            }
        }

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = findViewById(R.id.action_search)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        searchView?.isIconified = false
        searchView?.clearFocus()

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val sharedPref = PreferenceManager
                    .getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(UrlAddress.FLICKR_QUERY, query).apply()
                searchView?.clearFocus()
                searchView?.setQuery("", false)

                val directions = SearchListFragmentDirections
                    .actionSearchToSearchResults()
                navController.navigate(directions)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Don NOT Delete Unused function
                // OnQueryTextListener require override
                return false
            }
        })
    }

}
