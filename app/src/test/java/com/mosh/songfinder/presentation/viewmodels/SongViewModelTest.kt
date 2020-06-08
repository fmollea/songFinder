package com.mosh.songfinder.presentation.viewmodels

import com.mosh.songfinder.data.repository.SongRepository
import org.junit.Before
import org.mockito.Mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mosh.songfinder.data.dao.entity.SearchEntity
import com.mosh.songfinder.data.dao.entity.SongEntity
import com.mosh.songfinder.utils.TestContextProvider
import com.mosh.songfinder.utils.TestCoroutineRules
import com.mosh.songfinder.data.services.data.SongsResponse
import com.mosh.songfinder.domain.Search
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.viewmodels.SongViewModel.SongViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SongViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRules()

    @Mock
    private lateinit var repository: SongRepository

    @Mock
    private lateinit var viewStateObserver : Observer<SongViewState>

    private lateinit var viewModel: SongViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SongViewModel(
            repository,
            TestContextProvider()
        ).apply {
            getStateLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `test get songs from server success`() {
        testCoroutineRule.runBlockingTest {
            val data =  Response.success(mock(SongsResponse::class.java))
            val term = "term"

            `when`(repository.getSongsFromServer(term)).thenReturn(data)
            viewModel.getSongsFromServer(term)

            verify(viewStateObserver).onChanged(SongViewState.Loading)
            verify(viewStateObserver).onChanged(SongViewState.SuccessSong(
                data.body()?.toListSongs() ?: emptyList()))
        }
    }

    @Test
    fun `test get songs from server fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            val term = "term"

            `when`(repository.getSongsFromServer(term)).thenThrow(error)
            viewModel.getSongsFromServer(term)

            verify(viewStateObserver).onChanged(SongViewState.Loading)
            verify(viewStateObserver).onChanged(SongViewState.Error(error))
        }
    }

    @Test
    fun `test get songs from data base success`() {
        testCoroutineRule.runBlockingTest {
            val songEntity = mock(SongEntity::class.java)
            val data = listOf<SongEntity>(songEntity, songEntity, songEntity)
            val term = "term"

            `when`(repository.getAllSongBySearchId(term)).thenReturn(data)
            `when`(songEntity.toSong()).thenReturn(mock(Song::class.java))
            viewModel.getSongsFromDB(term)

            verify(viewStateObserver).onChanged(SongViewState.Loading)
            verify(viewStateObserver).onChanged(SongViewState.SuccessSong(
                data.map { item -> item.toSong() }))
        }
    }

    @Test
    fun `test insert songs to data base`() {
        testCoroutineRule.runBlockingTest {
            val song = mock(Song::class.java)
            val songs = listOf<Song>(song, song, song)
            val term = "term"

            `when`(song.toEntity(term)).thenReturn(mock(SongEntity::class.java))

            viewModel.insertSongToDB(songs, term)
            verify(repository).insertOrUpdateSongAll(songs.map { item -> item.toEntity(term) })

        }
    }

    @Test
    fun `test get search from data base succes`() {
            val data = mock(LiveData::class.java) as LiveData<List<SearchEntity>>
            `when`(repository.getAllSearch()).thenReturn(data)
            viewModel.getSearchsFromDB()
            verify(repository).getAllSearch()
    }

    @Test
    fun `test insert search to data base`() {
        testCoroutineRule.runBlockingTest {
            val search = mock(Search::class.java)
            viewModel.insertSearchToDB(search)
            verify(repository).insertOrUpdateSearch(search.toSearchEntity())
        }
    }
}