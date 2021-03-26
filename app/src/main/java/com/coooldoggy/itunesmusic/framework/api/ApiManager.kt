package com.coooldoggy.itunesmusic.framework.api

import android.content.Context
import com.coooldoggy.itunesmusic.framework.data.SongSearchResult
import retrofit2.Response


object ApiManager {
    private val TAG = ApiManager::class.java.simpleName
    private lateinit var songSearchApiService: SongSearchApiService

    fun createAPI(context: Context): SongSearchApiService{
        songSearchApiService = NetworkManager().createService(SongSearchApiService::class.java, context)
        return songSearchApiService
    }

    suspend fun querySong(): Response<SongSearchResult>{
        return songSearchApiService.querySong()
    }
}