package com.mosh.songfinder.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SongTest {

    private lateinit var data: Song

    @Before
    fun setUp() {
        data = Song(COLLECTION_ID, ARTIST_NAME, COLLECTION_NAME, PREVIEW_URL, ARTWORKURL_100,
            COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
    }

    @Test
    fun constructorTest() {
        Assert.assertNotNull(data)
    }

    @Test
    fun checkCollectionId() {
        Assert.assertEquals(COLLECTION_ID, data.collectionId)
    }

    @Test
    fun checkArtistName() {
        Assert.assertEquals(ARTIST_NAME, data.artistName)
    }

    @Test
    fun checkCollectionName() {
        Assert.assertEquals(COLLECTION_NAME, data.collectionName)
    }

    @Test
    fun checkPreviewUrl() {
        Assert.assertEquals(PREVIEW_URL, data.previewUrl)
    }

    @Test
    fun checkArtworkUrl100() {
        Assert.assertEquals(ARTWORKURL_100, data.artworkUrl100)
    }

    @Test
    fun checkCollectionPrice() {
        Assert.assertEquals(COLLECTION_PRICE, data.collectionPrice, DELTA)
    }

    @Test
    fun checkTrackPrice() {
        Assert.assertEquals(TRACK_PRICE, data.trackPrice, DELTA)
    }

    @Test
    fun checkTrackTimeMillis() {
        Assert.assertEquals(TRACK_TIME_MILLIS, data.trackTimeMillis)
    }

    @Test
    fun checkCurrency() {
        Assert.assertEquals(CURRENCY, data.currency)
    }

    @Test
    fun checkPrimaryGenreName() {
        Assert.assertEquals(PRIMARY_GENRE_NAME, data.primaryGenreName)
    }

    @Test
    fun checkIsStreamable() {
        Assert.assertEquals(IS_STREAMABLE, data.isStreamable)
    }

    companion object {
        const val COLLECTION_ID = 1
        const val ARTIST_NAME = "artistName"
        const val COLLECTION_NAME = "collection name"
        const val PREVIEW_URL = "previewUrl"
        const val ARTWORKURL_100 = "artUrl"
        const val COLLECTION_PRICE = 1.0
        const val TRACK_PRICE = 1.0
        const val TRACK_TIME_MILLIS = 1
        const val CURRENCY = "currency"
        const val PRIMARY_GENRE_NAME = "genreName"
        const val IS_STREAMABLE = true
        const val DELTA = 1e-15;
    }
}