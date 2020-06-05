package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider

@Suppress("UNCHECKED_CAST")
class SongViewModelFactory(
    private val repository: SongRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = SongViewModel(repository, contextProvider) as T
}