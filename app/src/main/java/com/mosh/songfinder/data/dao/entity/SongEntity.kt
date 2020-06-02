package com.mosh.songfinder.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_db")
data class SongEntity(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "search_id")
    val searchId: Int,
    @ColumnInfo(name = "artist_name")
    val artistName: String,
    @ColumnInfo(name = "collection_name")
    val collectionName: String,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    @ColumnInfo(name = "artwork_url_100")
    val artworkUrl100: String,
    @ColumnInfo(name = "collection_price")
    val collectionPrice: Double,
    @ColumnInfo(name = "track_price")
    val trackPrice: Double,
    @ColumnInfo(name = "track_time_millis")
    val trackTimeMillis: Int,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "primary_genre_name")
    val primaryGenreName: String,
    @ColumnInfo(name = "is_streamable")
    val isStreamable: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}