package com.coooldoggy.itunesmusic.framework.api

import com.coooldoggy.itunesmusic.QUERY_SUB_URL
import com.coooldoggy.itunesmusic.framework.data.SongSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongSearchApiService {

    @GET(QUERY_SUB_URL)
    suspend fun querySong(
        @Query("term") term: String = "greenday",
        @Query("entity") entity: String = "song",
        @Query("offset") offset: Int = 0
    ): Response<SongSearchResult>
}