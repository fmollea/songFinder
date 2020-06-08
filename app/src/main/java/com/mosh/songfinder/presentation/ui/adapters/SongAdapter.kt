package com.mosh.songfinder.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mosh.songfinder.R
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.ui.fragments.SongListFragment
import com.mosh.songfinder.presentation.ui.fragments.SongListFragmentDirections
import com.mosh.songfinder.utils.Utils
import kotlinx.android.synthetic.main.item_song_row.view.*

class SongAdapter(
    private val fragment: SongListFragment,
    var items: List<Song>
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SongViewHolder(LayoutInflater
        .from(parent.context).inflate(R.layout.item_song_row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentItem = items[position]

        holder.itemView.tvTrack.text = currentItem.trackName
        holder.itemView.tvArtist.text = currentItem.artistName
        Glide.with(fragment.requireContext())
            .applyDefaultRequestOptions(
                RequestOptions()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default))
            .load(currentItem.artworkUrl100)
            .into(holder.itemView.ivImage)

        holder.itemView.setOnClickListener {
            if(Utils.isConnected(fragment.requireContext())) {
                val action = SongListFragmentDirections.actionSongListFragmentToCollectionFragment(
                    currentItem.collectionId)
                fragment.findNavController().navigate(action)
            } else {
                Toast.makeText(fragment.context,"Must be connected to the internet", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
