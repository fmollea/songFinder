package com.mosh.songfinder.presentation.viewmodels

import com.mosh.songfinder.data.repository.SongRepository
import org.junit.Before
import org.mockito.Mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mosh.songfinder.utils.TestContextProvider
import com.mosh.songfinder.utils.TestCoroutineRule
import com.mosh.songfinder.data.services.data.SongsResponse
import com.mosh.songfinder.presentation.viewmodels.SongViewModel.SongViewState
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SongViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

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
            verify(viewStateObserver).onChanged(SongViewState.Success(
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
}