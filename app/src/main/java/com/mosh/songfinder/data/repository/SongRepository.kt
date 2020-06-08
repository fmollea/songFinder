package com.mosh.songfinder.data.repository

import com.google.gson.GsonBuilder
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.dao.entity.SearchEntity
import com.mosh.songfinder.data.dao.entity.SongEntity
import com.mosh.songfinder.data.services.SongApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongRepository(
    private val db: AppDataBase
) {
    private val client = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(SongApi::class.java)

    suspend fun getSongsFromServer(term: String) = client.getSongsFromServer(term)

    suspend fun insertOrUpdateSong(item: SongEntity) = db.getSongDao().insertOrUpdate(item)

    suspend fun insertOrUpdateSongAll(items: List<SongEntity>) = db.getSongDao().insertOrUpdateAll(items)

    suspend fun getAllSongBySearchId(term: String) = db.getSongDao().getAllSongBySearchId(term)

    suspend fun insertOrUpdateSearch(search: SearchEntity) = db.getSearchDao().insertOrUpdate(search)

    fun getAllSearch() = db.getSearchDao().getAll()
}