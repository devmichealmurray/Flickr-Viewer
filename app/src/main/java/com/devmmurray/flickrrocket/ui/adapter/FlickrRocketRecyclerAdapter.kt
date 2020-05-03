package com.devmmurray.flickrrocket.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.squareup.picasso.Picasso


class FlickrViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val photoHolder: ImageView = view.findViewById(R.id.listItemImageView)

    fun bindPhoto(item: PhotoObject) {
        Picasso.get()
            .load(item.link)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder)
            .fit()
            .into(photoHolder)
    }
}


class FlickrRocketRecyclerAdapter(private val List: ArrayList<PhotoObject>) :
    RecyclerView.Adapter<FlickrViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_photo, parent, false)
        return FlickrViewHolder(view)
    }

    override fun getItemCount(): Int = List.size

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.bindPhoto(List[position])
    }

    fun updatePhotoList(newList: ArrayList<PhotoObject>) {
        Log.d("Update Photo List", "$newList")
        List.clear()
        List.addAll(newList)
        notifyDataSetChanged()
    }
}