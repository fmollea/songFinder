package com.mosh.songfinder.presentation.viewmodels

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.mock
import java.util.*

class SongViewModelTest {

    @Mock
    private lateinit var repository: SongRepository

    private lateinit var viewModel: SongViewModel

    @Before
    fun before() {
        viewModel = SongViewModel(repository)
    }

}