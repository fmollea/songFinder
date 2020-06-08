package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosh.songfinder.data.repository.CollectionRepository
import com.mosh.songfinder.domain.CollectionSong
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectionViewModel(
    private val repository: CollectionRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = CollectionViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<CollectionViewState>()
    fun getStateLiveData(): LiveData<CollectionViewState> = stateLiveData

    fun getCollectionFromServer(idCollection: Int) {
        stateLiveData.value = CollectionViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getCollectionFromServer(idCollection)
            }
            val collectionSong = data.body()?.let {
                it.toCollection()
            }
            stateLiveData.value = CollectionViewState.Success(collectionSong)
        }
    }

    sealed class CollectionViewState {
        object Loading: CollectionViewState()
        data class Error(val throwable: Throwable) : CollectionViewState()
        data class Success(val data: CollectionSong?) : CollectionViewState()
    }
}