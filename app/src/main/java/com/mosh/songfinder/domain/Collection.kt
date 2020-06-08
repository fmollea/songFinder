package com.mosh.songfinder.domain

data class Collection(
    val collectionName: String,
    val artistName: String,
    val songs: List<Song>,
    val artUrl100: String
)