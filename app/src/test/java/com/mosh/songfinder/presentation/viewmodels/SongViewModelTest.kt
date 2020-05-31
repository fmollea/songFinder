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
import org.mockito.MockitoAnnotations
import java.util.*

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
}