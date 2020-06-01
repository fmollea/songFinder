package com.mosh.songfinder.presentation.viewmodels

import com.mosh.songfinder.data.repository.SongRepository
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mosh.songfinder.coroutine.TestContextProvider
import com.mosh.songfinder.coroutine.TestCoroutineRule
import com.mosh.songfinder.presentation.viewmodels.SongViewModel.SongViewState
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SongViewModelTest {

    @get:Rule
    private val instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    private val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: SongRepository

    @Mock
    private lateinit var viewStateObserver : Observer<SongViewState>

    private lateinit var viewModel: SongViewModel

    @Before
    fun setup() {
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
            val data = Any()
            val term = "term"

            `when`(repository.getSongsFromServer(term)).thenReturn(data)
            viewModel.getSongsFromServer(term)

            verify(viewStateObserver).onChanged(SongViewState.Loading)
            verify(viewStateObserver).onChanged(SongViewState.Success(data))
        }
    }

    @Test
    fun `test get songs from server fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            val term = "term"

            `when`(repository.getSongsFromServer(term)).thenReturn(error)
            viewModel.getSongsFromServer(term)

            verify(viewStateObserver).onChanged(SongViewState.Loading)
            verify(viewStateObserver).onChanged(SongViewState.Error(error))
        }
    }
}