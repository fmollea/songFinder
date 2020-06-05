package com.mosh.songfinder.domain

import com.mosh.songfinder.data.dao.entity.SongEntity
import java.io.Serializable

data class Song(
    val collectionId: Int,
    val artistName: String,
    val trackName: String,
    val collectionName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val collectionPrice: Double,
    val trackPrice: Double,
    val trackTimeMillis: Int,
    val currency: String,
    val primaryGenreName: String,
    val isStreamable: Boolean
) : Serializable {
    fun toEntity(idSearch: Int) = SongEntity(collectionId, idSearch, artistName, trackName, collectionName, previewUrl,
        artworkUrl100, collectionPrice, trackPrice, trackTimeMillis, currency, primaryGenreName, isStreamable)
}
