package com.mosh.songfinder.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mosh.songfinder.data.dao.entity.SearchEntity
import com.mosh.songfinder.utils.LiveDataTestUtil
import com.mosh.songfinder.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SearchDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var appDataBase: AppDataBase
    private lateinit var searchDao: SearchDao

    @Before
    @Throws(Throwable::class)
    fun initDb() {
        appDataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build();

        searchDao = appDataBase.getSearchDao()
    }

    @After
    @Throws(Throwable::class)
    fun closeDb() {
        appDataBase.close()
    }

    @Test
    fun testInsertorUpdateASearchInDb() {
        testCoroutineRule.runBlockingTest {
            val search = SearchEntity(TERM)

            searchDao.insertOrUpdate(search)
            Assert.assertEquals(search, LiveDataTestUtil.getValue(searchDao.get(search.term)))
        }
    }

    @Test
    fun testGetSongsAndEmptyDb() {
        val list: List<SearchEntity>? = LiveDataTestUtil.getValue(searchDao.getAll())
        Assert.assertTrue(list!!.isEmpty())
    }

    companion object {
        const val TERM = "term"
    }
}