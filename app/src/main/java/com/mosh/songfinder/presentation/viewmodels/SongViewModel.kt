package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModel(
    private val repository: SongRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = SongViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<SongViewState>()
    fun getStateLiveData(): LiveData<SongViewState> = stateLiveData

    fun getSongsFromServer(term: String) {
        stateLiveData.value = SongViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getSongsFromServer(term)
            }
            stateLiveData.value = SongViewState.Success(data)
        }
    }


    sealed class SongViewState {
        object Loading : SongViewState()
        data class Error(val throwable: Throwable) : SongViewState()
        data class Success(val data: Any) : SongViewState()
    }
}