package com.mosh.songfinder.data.services.data


import com.google.gson.annotations.SerializedName
import com.mosh.songfinder.domain.Song

data class SongsResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songs: List<Song>
)