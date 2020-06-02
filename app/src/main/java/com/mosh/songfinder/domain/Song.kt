package com.mosh.songfinder.domain

import java.io.Serializable

data class Song(
    val collectionId: Int,
    val artistName: String,
    val collectionName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val collectionPrice: Double,
    val trackPrice: Double,
    val trackTimeMillis: Int,
    val currency: String,
    val primaryGenreName: String,
    val isStreamable: Boolean
) : Serializable
