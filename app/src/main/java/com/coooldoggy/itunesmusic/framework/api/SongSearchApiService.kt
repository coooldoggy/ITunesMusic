package com.coooldoggy.itunesmusic.framework.api

import com.coooldoggy.itunesmusic.framework.data.SongSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongSearchApiService {

    @GET("search")
    suspend fun querySong(
        @Query("term") term: String = "greenday",
        @Query("entity") entity: String = "song"
    ): Response<SongSearchResult>
}