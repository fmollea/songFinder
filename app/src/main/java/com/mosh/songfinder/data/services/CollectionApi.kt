package com.mosh.songfinder.data.services

import com.mosh.songfinder.data.services.data.SongsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionApi {

    @GET("lookup")
    suspend fun getCollectionFromServer(
        @Query("id") id: Int,
        @Query("entity") mediaType: String = "song"
    ): Response<SongsResponse>
}