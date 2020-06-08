package com.mosh.songfinder.data.services.data


import com.google.gson.annotations.SerializedName
import com.mosh.songfinder.domain.Collection
import com.mosh.songfinder.domain.Song

data class CollectionResponse(
    @SerializedName("resultCount") val resultCount: Int?,
    @SerializedName("results") val results: List<CollectionResult>
) {
    fun toCollection(): Collection {
        val collection = results[0]
        val songs = results.drop(1).map { item -> Song(item.collectionId?: -1, item.artistName?: "",
            item.trackName?: "", item.collectionName?:"", item.previewUrl?:"", item.artworkUrl100?:"",
            item.collectionPrice?:-1.0, item.trackPrice?:-1.0, item.trackTimeMillis?:0, item.currency?:"",
            item.primaryGenreName?:"", item.isStreamable?:false) }

        return Collection(collection.collectionName?:"", collection.artistName?:"", songs, collection.artworkUrl100?:"")
    }
}