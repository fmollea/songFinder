package com.mosh.songfinder.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SongTest {

    private lateinit var data: Song

    @Before
    fun setUp() {
        data = Song(artistId, collectionId, trackId, artistName, collectionName, trackName, previewUrl, artworkUrl100,
            collectionPrice, trackPrice, trackTimeMillis, currency, primaryGenreName, isStreamable)
    }

    @Test
    fun constructorTest() {
        Assert.assertNotNull(data)
    }

    @Test
    fun checkArtistId() {
        Assert.assertEquals(artistId, data.artistId)
    }

    @Test
    fun checkCollectionId() {
        Assert.assertEquals(collectionId, data.collectionId)
    }

    @Test
    fun checkTrackId() {
        Assert.assertEquals(trackId, data.trackId)
    }

    @Test
    fun checkArtistName() {
        Assert.assertEquals(artistName, data.artistName)
    }

    @Test
    fun checkCollectionName() {
        Assert.assertEquals(collectionName, data.collectionName)
    }

    @Test
    fun checkTrackName() {
        Assert.assertEquals(trackName, data.trackName)
    }

    @Test
    fun checkPreviewUrl() {
        Assert.assertEquals(previewUrl, data.previewUrl)
    }

    @Test
    fun checkArtworkUrl100() {
        Assert.assertEquals(artworkUrl100, data.artworkUrl100)
    }

    @Test
    fun checkCollectionPrice() {
        Assert.assertEquals(collectionPrice, data.collectionPrice)
    }

    @Test
    fun checkTrackPrice() {
        Assert.assertEquals(trackPrice, data.trackPrice)
    }

    @Test
    fun checkTrackTimeMillis() {
        Assert.assertEquals(trackTimeMillis, data.trackTimeMillis)
    }

    @Test
    fun checkCurrency() {
        Assert.assertEquals(currency, data.currency)
    }

    @Test
    fun checkPrimaryGenreName() {
        Assert.assertEquals(primaryGenreName, data.primaryGenreName)
    }

    @Test
    fun checkIsStreamable() {
        Assert.assertEquals(isStreamable, data.isStreamable)
    }

    companion object {
        const val artistId = 1
        const val collectionId = 1
        const val trackId = 1
        const val artistName = "artistName"
        const val collectionName = "collection name"
        const val trackName = "trackName"
        const val previewUrl = "previewUrl"
        const val artworkUrl100 = "artUrl"
        const val collectionPrice = 1.0
        const val trackPrice = 1.0
        const val trackTimeMillis = 1
        const val currency = "currency"
        const val primaryGenreName = "genreName"
        const val isStreamable = true
    }
}