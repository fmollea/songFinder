package com.mosh.songfinder.data.services.data

import com.google.gson.annotations.SerializedName
import com.mosh.songfinder.domain.Song

data class SongsResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songs: List<Result>
) {
    fun toListSongs() = songs.map { item ->
        Song(
            item.collectionId,
            item.artistName ?: "",
            item.trackName?: "",
            item.collectionName ?: "",
            item.previewUrl ?: "",
            item.artworkUrl100 ?: "",
            item.collectionPrice ?: 0.0,
            item.trackPrice ?: 0.0,
            item.trackTimeMillis,
            item.currency,
            item.primaryGenreName ?: "",
            item.isStreamable
        )
    }
}