package com.mosh.songfinder.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mosh.songfinder.R
import com.mosh.songfinder.domain.Song
import kotlinx.android.synthetic.main.item_song_row.view.*

class SongAdapter(
    private val context: Context,
    var items: List<Song>
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SongViewHolder(LayoutInflater
        .from(parent.context).inflate(R.layout.item_song_row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentItem = items[position]

        holder.itemView.tvTrack.text = currentItem.trackName
        holder.itemView.tvArtist.text = currentItem.artistName
        Glide.with(context)
            .applyDefaultRequestOptions(
                RequestOptions()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default))
            .load(currentItem.artworkUrl100)
            .into(holder.itemView.ivImage)
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
