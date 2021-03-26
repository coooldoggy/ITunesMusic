package com.coooldoggy.itunesmusic.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.coooldoggy.itunesmusic.framework.api.ApiManager
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseViewModel
import com.coooldoggy.itunesmusic.ui.common.BaseViewModelEvent
import kotlinx.coroutines.launch

class TrackListViewModel (application: Application): BaseViewModel(application){
    private val TAG = TrackListViewModel::class.java.simpleName
    var trackList = arrayListOf<Song>()

    companion object{
        const val EVENT_TRACK_LOADED = 1000
    }

     fun loadTrackList(){
        sendToEvent.value = BaseViewModelEvent.ShowProgressBar()
        viewModelScope.launch {
            kotlin.runCatching {
               ApiManager.querySong(getApplication())?.let { response ->
                   val resultList = response.body()?.results ?: emptyList<Song>()
                   trackList.addAll(resultList)
               }
                sendToEvent.value = BaseViewModelEvent.Event(EVENT_TRACK_LOADED, "")
            }.onFailure {
                it.printStackTrace()
            }
        }

        sendToEvent.value = BaseViewModelEvent.HideProgressBar()
    }
}