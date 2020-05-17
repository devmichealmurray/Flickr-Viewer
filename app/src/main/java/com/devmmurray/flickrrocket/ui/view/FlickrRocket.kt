package com.devmmurray.flickrrocket.ui.view

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.UrlAddress
import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class FlickrRocket : AppCompatActivity() {

    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var navController: NavController
    private var searchView: SearchView? = null
    private val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Resets theme after splash screen
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        // Sets app to fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Live Data Observers
        baseViewModel.ioException.observe(this, ioExceptionObserver)
        baseViewModel.exception.observe(this, exceptionObserver)
        baseViewModel.refresh(false)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        // Sets Up Bottom Navigation Bar
        bottomNavBar = findViewById(R.id.bottom_nav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavBar,
            navHostFragment!!.navController
        )
        navController = navHostFragment.navController

        // Destination Listener decides when to show Search Toolbar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search ->
                    toolBar.visibility = View.VISIBLE
                else ->
                    toolBar.visibility = View.GONE
            }
        }

        // Configuration and Set Up of Search Toolbar
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = findViewById(R.id.action_search)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        searchView?.isIconified = false
        searchView?.clearFocus()

        // Saves search request to sharedPrefs and used for Api call
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
                // Don NOT remove unused function
                // OnQueryTextListener required override
                return false
            }
        })
    }

    //Exception Alert if app cannot connect to internet
    private val ioExceptionObserver = Observer<Boolean> { exception ->
        if (exception) {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Internet Connction")
                .setMessage("UH OH!\nNo Internet Connection Found.\nWould you like to adjust your settings?")
                .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .show()
        }
    }

    // Exception Alert for unknown errors
    private val exceptionObserver = Observer<String> { message ->
        if (message.isNotEmpty()) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Unknown Error")
                .setMessage(message)
                .setPositiveButton(R.string.ok) { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .show()
        }
    }

}