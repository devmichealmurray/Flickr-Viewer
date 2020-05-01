package com.devmmurray.flickrrocket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.flickrrocket.R

class SuggestionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val buttonText: TextView = view.findViewById(R.id.suggestionButton)
    fun onBind(suggestion: String) {
        buttonText.text = suggestion
    }
}

class SuggestionsRecyclerView(private val suggestionsList: ArrayList<String>) :
    RecyclerView.Adapter<SuggestionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.suggestion_recycler_item, parent, false)
        return SuggestionsViewHolder(view)
    }

    override fun getItemCount() = suggestionsList.size


    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.onBind(suggestionsList[position])
    }
}