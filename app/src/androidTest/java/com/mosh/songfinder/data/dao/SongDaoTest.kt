package com.mosh.songfinder.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.utils.LiveDataTestUtil
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDataBase: AppDataBase
    private lateinit var songDao: SongDao

    @Before
    @Throws(Throwable::class)
    fun initDb() {
        appDataBase = Room.inMemoryDatabaseBuilder<AppDataBase>(
            InstrumentationRegistry.getInstrumentation().context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build();

        songDao = appDataBase.songDao()
    }

    @After
    @Throws(Throwable::class)
    fun closeDb() {
        appDataBase.close()
    }

    @Test
    fun testInsertorUpdateASongInDb() {
        var song = Song(ARTIST_ID, COLLECTION_ID, TRACK_ID, ARTIST_NAME, COLLECTION_NAME, TRACK_NAME, PREVIEW_URL, ARTWORKURL_100,
            COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
        song.id = 1

        songDao.insertOrUpdate(song)
        Assert.assertEquals(song, songDao.getSong(1))
    }

    @Test
    fun testDeleteASongInDb() {
        val song = Song(ARTIST_ID, COLLECTION_ID, TRACK_ID, ARTIST_NAME, COLLECTION_NAME, TRACK_NAME, PREVIEW_URL, ARTWORKURL_100,
            COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
        song.id = 1

        songDao.insertOrUpdate(song)
        songDao.delete(song)
        Assert.assertNull(songDao.getSong(1))
    }

    @Test
    fun testGetSongsAndEmptyDb() {
        val list: List<Song>? = LiveDataTestUtil.getValue(songDao.getAll())
        Assert.assertTrue(list!!.isEmpty())
    }

    @Test
    fun testGetSongsByCollectionId() {
        for(i in 1..5) {
            val song = Song(ARTIST_ID, COLLECTION_ID, TRACK_ID, ARTIST_NAME, COLLECTION_NAME, TRACK_NAME, PREVIEW_URL, ARTWORKURL_100,
                COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
            song.id = i
            songDao.insertOrUpdate(song)
        }

        val list: List<Song>? = LiveDataTestUtil.getValue(songDao.getByCollectionId(COLLECTION_ID))
        Assert.assertEquals(5, list!!.size)
    }

    companion object {
        const val ARTIST_ID = 1
        const val COLLECTION_ID = 1
        const val TRACK_ID = 1
        const val ARTIST_NAME = "artistName"
        const val COLLECTION_NAME = "collection name"
        const val TRACK_NAME = "trackName"
        const val PREVIEW_URL = "previewUrl"
        const val ARTWORKURL_100 = "artUrl"
        const val COLLECTION_PRICE = 1.0
        const val TRACK_PRICE = 1.0
        const val TRACK_TIME_MILLIS = 1
        const val CURRENCY = "currency"
        const val PRIMARY_GENRE_NAME = "genreName"
        const val IS_STREAMABLE = true
    }
}