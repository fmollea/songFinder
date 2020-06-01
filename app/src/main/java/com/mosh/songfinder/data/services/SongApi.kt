package com.mosh.songfinder.data.services

import com.mosh.songfinder.data.services.data.SongsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongApi {

    @GET("search")
    suspend fun getSongsFromServer(
        @Query("term") term: String,
        @Query("mediaType") mediaType: String = "music",
        @Query("limit") limit: Int = 20
    ): Response<SongsResponse>
}