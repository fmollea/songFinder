package com.mosh.songfinder.presentation.viewmodels

import com.mosh.songfinder.data.repository.SongRepository
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SongViewModelTest {

    @Mock
    private lateinit var repository: SongRepository

    private lateinit var viewModel: SongViewModel

    @Before
    fun before() {
        viewModel = SongViewModel(repository)
    }

}