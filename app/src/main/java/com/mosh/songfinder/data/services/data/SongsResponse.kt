package com.mosh.songfinder.data.services.data


import com.google.gson.annotations.SerializedName

data class SongsResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
)