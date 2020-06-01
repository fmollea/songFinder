package com.mosh.songfinder.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Song(
    @SerializedName("artistId") val artistId: Int,
    @SerializedName("collectionId") val collectionId: Int,
    @SerializedName("trackId") val trackId: Int,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("collectionPrice") val collectionPrice: Double,
    @SerializedName("trackPrice") val trackPrice: Double,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int,
    @SerializedName("currency") val currency: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("isStreamable") val isStreamable: Boolean
) : Serializable
