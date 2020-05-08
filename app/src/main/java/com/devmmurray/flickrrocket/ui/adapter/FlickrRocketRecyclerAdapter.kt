package com.devmmurray.flickrrocket.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.data.model.UrlAddress
import com.devmmurray.flickrrocket.ui.view.SearchListFragmentDirections
import com.devmmurray.flickrrocket.ui.view.SearchResultsDirections
import com.squareup.picasso.Picasso

class FlickrViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val view = view

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

    fun bindPhotoSearchFrag(item: PhotoObject, position: Int, flags: RecyclerFlags) {
        Log.d("Recycler.bindPhoto", "******************** $position **************************")
        val photoHolder: ImageView = view.findViewById(R.id.listItemImageView)
        photoHolder.setOnClickListener {
            when (flags) {
                RecyclerFlags.SEARCH -> {
                    val directions = SearchListFragmentDirections
                        .actionListToDetail(position + 1)
                    Navigation.findNavController(photoHolder).navigate(directions)
                }
                else -> {
                    val directions = SearchResultsDirections
                        .actionSearchResultsToHome(position + 1)
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
    private val flag = flags

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        Log.d("FlickrRecycler", "*********** List Size = ${list.size} **********")
        val inflater = LayoutInflater.from(parent.context)

        return FlickrViewHolder(
            view = when (flag) {
                RecyclerFlags.SEARCH, RecyclerFlags.SEARCH_RESULTS ->
                    inflater.inflate(R.layout.item_photo, parent, false)
                else ->
                    inflater.inflate(R.layout.suggestion_recycler_item, parent, false)
            }
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        when (flag) {
            RecyclerFlags.SEARCH, RecyclerFlags.SEARCH_RESULTS ->
                holder.bindPhotoSearchFrag(list[position] as PhotoObject, position, flag)
            else ->
                holder.bindSuggestion(list[position] as String)
        }
    }

    fun updatePhotoList(newList: ArrayList<PhotoObject>) {
        Log.d("Update Photo List", "$newList")
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}