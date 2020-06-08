package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mosh.songfinder.data.repository.CollectionRepository
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider


@Suppress("UNCHECKED_CAST")
class CollectionViewModelFactory(
    private val repository: CollectionRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = CollectionViewModel(repository, contextProvider) as T
}