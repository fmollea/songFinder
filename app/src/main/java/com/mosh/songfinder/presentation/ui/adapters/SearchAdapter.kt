package com.mosh.songfinder.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mosh.songfinder.R
import com.mosh.songfinder.domain.Search
import kotlinx.android.synthetic.main.item_song_row.view.*

class SearchAdapter(
    private val context: Context,
    var items: List<Search>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_search_row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.tvTrack.text = currentItem.term
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}