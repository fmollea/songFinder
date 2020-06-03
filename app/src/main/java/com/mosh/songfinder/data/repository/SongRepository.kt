package com.mosh.songfinder.data.repository

import com.google.gson.GsonBuilder
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.dao.entity.SongEntity
import com.mosh.songfinder.data.services.SongApi
import com.mosh.songfinder.domain.Song
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

    suspend fun insertOrUpdate(item: SongEntity) = db.getSongDao().insertOrUpdate(item)

    suspend fun delete(item: SongEntity) = db.getSongDao().delete(item)

    fun getAllShoppingItems() = db.getSongDao().getAll()

    fun getAllBySearchId(idSearch: Int) = db.getSongDao().getAllBySearchId(idSearch)
}