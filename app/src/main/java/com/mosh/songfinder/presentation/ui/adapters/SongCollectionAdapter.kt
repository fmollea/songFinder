package com.mosh.songfinder.presentation.ui.adapters

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mosh.songfinder.R
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.utils.Utils
import kotlinx.android.synthetic.main.item_song_preview_row.view.*

class SongCollectionAdapter (
    private val context: Context,
    var items: List<Song>
) : RecyclerView.Adapter<SongCollectionAdapter.SongCollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= SongCollectionViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_song_preview_row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SongCollectionViewHolder, position: Int) {
        var mediaPlayer = MediaPlayer()
        val currentItem = items[position]

        holder.itemView.tvSongCollection.text = currentItem.trackName
        if (currentItem.isStreamable) {
            Glide.with(context).load(R.drawable.ic_play).into(holder.itemView.ivMediaPlayer)

            holder.itemView.ivMediaPlayer.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    Glide.with(context).load(R.drawable.ic_play).into(holder.itemView.ivMediaPlayer)
                } else {
                    if (Utils.isConnected(context)) {
                        mediaPlayer = MediaPlayer().apply {
                            setAudioStreamType(AudioManager.STREAM_MUSIC)
                            setDataSource(currentItem.previewUrl)
                            prepare()
                            start()
                        }
                        Glide.with(context).load(R.drawable.ic_pause)
                            .into(holder.itemView.ivMediaPlayer)
                    } else {
                        Toast.makeText(context, "Must be connected to the internet", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Glide.with(context).load(R.drawable.ic_play_not_available).into(holder.itemView.ivMediaPlayer)
        }
    }

    inner class SongCollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}