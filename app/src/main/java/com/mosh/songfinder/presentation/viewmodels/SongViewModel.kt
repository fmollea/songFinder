package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModel(
    private val repository: SongRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = SongViewState.Loading.Hide
        stateLiveData.value = SongViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<SongViewState>()
    fun getStateLiveData(): LiveData<SongViewState> = stateLiveData

    fun getSongsFromServer(term: String) {
        stateLiveData.value = SongViewState.Loading.Show
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getSongsFromServer(term)
            }
            stateLiveData.value = SongViewState.Success(
                data.body()?.toListSongs() ?: emptyList()
            )
        }
    }

    fun insertSongToDB(songs: List<Song>, idSearch: Int) {
        viewModelScope.launch(handler) {
            withContext(contextProvider.IO) {
                songs.forEach {
                    repository.insertOrUpdateSong(it.toEntity(idSearch))
                }
            }
            stateLiveData.value = SongViewState.Loading.Hide
        }
    }

    fun getSongsFromDB(idSearch: Int) {
        stateLiveData.value = SongViewState.Loading.Show
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getAllSongBySearchId(idSearch)
            }
            stateLiveData.value = SongViewState.Loading.Hide
            stateLiveData.value = SongViewState.Success(
                data.value?.map { item -> item.toSong() } ?: emptyList()
            )
        }
    }

    sealed class SongViewState {
        sealed class Loading : SongViewState() {
            object Show : Loading()
            object Hide : Loading()
        }
        data class Error(val throwable: Throwable) : SongViewState()
        data class Success(val data: List<Song>) : SongViewState()
    }
}