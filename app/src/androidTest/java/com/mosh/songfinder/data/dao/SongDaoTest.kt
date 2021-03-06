package com.mosh.songfinder.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mosh.songfinder.utils.TestCoroutineRule
import com.mosh.songfinder.data.dao.entity.SongEntity
import com.mosh.songfinder.utils.LiveDataTestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var appDataBase: AppDataBase
    private lateinit var songDao: SongDao

    @Before
    @Throws(Throwable::class)
    fun initDb() {
        appDataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build();

        songDao = appDataBase.getSongDao()
    }

    @After
    @Throws(Throwable::class)
    fun closeDb() {
        appDataBase.close()
    }

    @Test
    fun testInsertorUpdateASongInDb() {
        testCoroutineRule.runBlockingTest {
            val song = SongEntity(COLLECTION_ID, TERM, ARTIST_NAME, TRACK_NAME, COLLECTION_NAME, PREVIEW_URL, ARTWORKURL_100,
                COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
            song.id = 1

            songDao.insertOrUpdate(song)
            Assert.assertEquals(song, LiveDataTestUtil.getValue(songDao.get(song.id!!)))
        }
    }

    @Test
    fun testInsertorUpdateAllSongsInDb() {
        testCoroutineRule.runBlockingTest {
            var songs = mutableListOf<SongEntity>()
            for(i in 1..5) {
                val song = SongEntity(COLLECTION_ID, TERM, ARTIST_NAME, TRACK_NAME, COLLECTION_NAME, PREVIEW_URL, ARTWORKURL_100,
                    COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
                songs.add(song)
            }
            songDao.insertOrUpdateAll(songs)
            Assert.assertEquals(songs.size, songDao.getAllSongBySearchId(TERM).size)
        }
    }

    @Test
    fun testGetSongsByCollectionId() {
        testCoroutineRule.runBlockingTest {
            for(i in 1..5) {
                val song = SongEntity(COLLECTION_ID, TERM, ARTIST_NAME, TRACK_NAME, COLLECTION_NAME, PREVIEW_URL, ARTWORKURL_100,
                    COLLECTION_PRICE, TRACK_PRICE, TRACK_TIME_MILLIS, CURRENCY, PRIMARY_GENRE_NAME, IS_STREAMABLE)
                songDao.insertOrUpdate(song)
            }

            val list: List<SongEntity>? = songDao.getAllSongBySearchId(TERM)
            Assert.assertEquals(5, list!!.size)
        }
    }

    companion object {
        const val COLLECTION_ID = 1
        const val TERM = "term"
        const val ARTIST_NAME = "artistName"
        const val TRACK_NAME = "trackName"
        const val COLLECTION_NAME = "collection name"
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