package com.devmmurray.flickrrocket.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.Photo
import com.squareup.picasso.Picasso


class FlickrViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val holder: ImageView =  view.findViewById(R.id.listItemImageView)
    fun bind(photo: Photo) {
        Log.d("Holder.Bind", "${photo.link}")
        Picasso.get()
            .load(photo.link)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder)
            .centerCrop()
            .into(holder)
    }
}

class FlickrRocketRecyclerAdapter(private val photoList: ArrayList<Photo>) :
    RecyclerView.Adapter<FlickrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_photo, parent, false)
        return FlickrViewHolder(view)
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.bind(photoList[position])

    }

    fun updatePhotoList(newList: ArrayList<Photo>) {
        Log.d("Update Photo List", "$newList")
        photoList.clear()
        photoList.addAll(newList)
        notifyDataSetChanged()
    }
}