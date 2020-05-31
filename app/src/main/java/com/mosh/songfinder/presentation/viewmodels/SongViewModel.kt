package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler

class SongViewModel(
    private val repository: SongRepository,
    private val contextProvider: CoroutineContextProvider
) {
    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = SongViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<SongViewState>()
    fun getStateLiveData() = stateLiveData

    sealed class SongViewState {
        object Loading : SongViewState()
        data class Error(val throwable: Throwable) : SongViewState()
        data class Success(val data: Any) : SongViewState()
    }
}