package com.devmmurray.flickrrocket.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.squareup.picasso.Picasso

class FlickrViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val view = view

    fun bindSuggestion(item: String) {
        val buttonText: TextView = view.findViewById(R.id.suggestionButton)
        buttonText.text = item
    }

    fun bindPhoto(item: PhotoObject) {
        val photoHolder: ImageView = view.findViewById(R.id.listItemImageView)
        Picasso.get()
            .load(item.link)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder)
            .resize(650, 650)
            .centerCrop()
            .into(photoHolder)
    }
}

class FlickrRocketRecyclerAdapter(private val List: ArrayList<Any>, flags: RecyclerFlags) :
    RecyclerView.Adapter<FlickrViewHolder>() {
    private val flag = flags

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return FlickrViewHolder(view = when (flag) {
            RecyclerFlags.SEARCH ->
                inflater.inflate(R.layout.item_photo, parent, false)
            else ->
                inflater.inflate(R.layout.suggestion_recycler_item, parent, false)
        })
    }

    override fun getItemCount(): Int = List.size

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        when (flag) {
            RecyclerFlags.SEARCH ->
                holder.bindPhoto(List[position] as PhotoObject)
            else ->
                holder.bindSuggestion(List[position] as String)
        }
     }

    fun updatePhotoList(newList: ArrayList<PhotoObject>) {
        Log.d("Update Photo List", "$newList")
        List.clear()
        List.addAll(newList)
        notifyDataSetChanged()
    }
}