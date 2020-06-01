package com.mosh.songfinder.data.repository

import com.google.gson.GsonBuilder
import com.mosh.songfinder.data.services.SongApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongRepository {

    private val client = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(SongApi::class.java)

    suspend fun getSongsFromServer(term: String) = client.getSongsFromServer(term)
}