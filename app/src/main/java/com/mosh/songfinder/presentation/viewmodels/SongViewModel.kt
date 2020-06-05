package com.mosh.songfinder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.domain.Search
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
            stateLiveData.value = SongViewState.SuccessSong(
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
        }
    }

    fun getSongsFromDB(idSearch: Int) {
        stateLiveData.value = SongViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getAllSongBySearchId(idSearch)
            }
            stateLiveData.value = SongViewState.SuccessSong(
                data.value?.map { item -> item.toSong() } ?: emptyList()
            )
        }
    }
    
    fun getSearchsFromDB() {
        stateLiveData.value = SongViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getAllSearch()
            }
            stateLiveData.value = SongViewState.SuccessSearch(
                data.value?.map { item -> item.toSearch() } ?: emptyList() 
            )
        }
    }

    fun insertSearchToDB(search: Search) {
        viewModelScope.launch(handler) {
            withContext(contextProvider.IO) {
                repository.insertOrUpdateSearch(search.toSearchEntity())
            }
        }
    }

    sealed class SongViewState {
        object Loading : SongViewState()
        data class Error(val throwable: Throwable) : SongViewState()
        data class SuccessSong(val data: List<Song>) : SongViewState()
        data class SuccessSearch(val data: List<Search>) : SongViewState()
    }
}