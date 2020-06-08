package com.mosh.songfinder.data.repository

import com.google.gson.GsonBuilder
import com.mosh.songfinder.data.services.CollectionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectionRepository {

    private val client = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(CollectionApi::class.java)

    suspend fun getCollectionFromServer(id: Int) = client.getCollectionFromServer(id)
}