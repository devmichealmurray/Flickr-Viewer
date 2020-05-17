package com.devmmurray.flickrrocket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.UrlAddress
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.ui.view.FavoritesFragmentDirections
import com.devmmurray.flickrrocket.ui.view.SearchListFragmentDirections
import com.devmmurray.flickrrocket.ui.view.SearchResultsDirections
import com.squareup.picasso.Picasso

/**
 *
 * Only one RecyclerView Adapter is used for this app.
 * Constant Flags are used to adjust the adapter dynamically
 * to the needs of that Recycler View
 *
 */

class FlickrViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val view = v

    /**
     *  The functions below are for binding the different recyclers and
     *  setting up the click listeners for each.
     *  Bind Photos uses Navigation Arguments to pass photo position and to
     *  inform the home fragment which list to use.
     */

    // Binding for the suggestions RV in search fragment
    fun bindSuggestion(item: String) {
        val buttonText: TextView = view.findViewById(R.id.suggestionButton)
        buttonText.text = item
        buttonText.setOnClickListener {
            val context = buttonText.context
            val sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context)
            sharedPref.edit().putString(UrlAddress.FLICKR_QUERY, item).apply()

            val directions = SearchListFragmentDirections
                .actionSearchToSearchResults()
            Navigation.findNavController(buttonText).navigate(directions)
        }
    }

    // Binding for search, search results, and favorites
    fun bindPhotos(item: PhotoObject, position: Int, flags: RecyclerFlags) {
        val photoHolder: ImageView = view.findViewById(R.id.listItemImageView)

        photoHolder.setOnClickListener {
            when (flags) {
                RecyclerFlags.SEARCH -> {
                    val directions = SearchListFragmentDirections
                        .actionListToDetail(position)
                    Navigation.findNavController(photoHolder).navigate(directions)
                }
                RecyclerFlags.FAVORITES -> {
                    val directions = FavoritesFragmentDirections
                        .actionFavoritesToDetail(true, position)
                    Navigation.findNavController(photoHolder).navigate(directions)
                }
                else -> {
                    val directions = SearchResultsDirections
                        .actionSearchResultsToHome(false, position)
                    Navigation.findNavController(photoHolder).navigate(directions)
                }

            }
        }

        Picasso.get()
            .load(item.link)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder)
            .resize(650, 650)
            .centerCrop()
            .into(photoHolder)
    }
}

class FlickrRocketRecyclerAdapter(private val list: ArrayList<Any>, flags: RecyclerFlags) :
    RecyclerView.Adapter<FlickrViewHolder>() {
    // Flags are used to switch from one recycler layout to another instead of writing several
    // adapter classes
    private val flag = flags

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return FlickrViewHolder(
            // Flag determines which layout will be inflated
            v = when (flag) {
                RecyclerFlags.SUGGESTIONS ->
                    inflater.inflate(R.layout.suggestion_recycler_item, parent, false)
                else ->
                    inflater.inflate(R.layout.item_photo, parent, false)
            }
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        // Flag determines which "Bind" function will be used
        when (flag) {
            RecyclerFlags.SUGGESTIONS ->
                holder.bindSuggestion(list[position] as String)
            else ->
                holder.bindPhotos(list[position] as PhotoObject, position, flag)
        }
    }

    fun updatePhotoList(newList: ArrayList<PhotoObject>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}