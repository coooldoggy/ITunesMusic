package com.coooldoggy.itunesmusic.ui.viewmodel

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.coooldoggy.itunesmusic.framework.api.ApiManager
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseViewModel
import com.coooldoggy.itunesmusic.ui.common.BaseViewModelEvent
import kotlinx.coroutines.launch

class TrackListViewModel (application: Application): BaseViewModel(application){
    private val TAG = TrackListViewModel::class.java.simpleName
    var trackList = arrayListOf<Song>()
    val offset = ObservableInt(0)

    companion object{
        const val EVENT_TRACK_LOADED = 1000
        const val EVENT_TRACK_LOAD_FAIL = EVENT_TRACK_LOADED + 1
    }

     fun loadTrackList(){
        sendToEvent.value = BaseViewModelEvent.ShowProgressBar()
        viewModelScope.launch {
            kotlin.runCatching {
               ApiManager.querySong(getApplication(), offset = offset.get())?.let { response ->
                   val resultList = response.body()?.results ?: emptyList<Song>()
                   val resultCount = response.body()?.resultCount ?: 0
                   offset.set(offset.get() + resultCount)
                   trackList.addAll(resultList)
               }
                sendToEvent.value = BaseViewModelEvent.Event(EVENT_TRACK_LOADED, "")
                sendToEvent.value = BaseViewModelEvent.HideProgressBar()
            }.onFailure {
                it.printStackTrace()
                sendToEvent.value = BaseViewModelEvent.Event(EVENT_TRACK_LOAD_FAIL, "")
                sendToEvent.value = BaseViewModelEvent.HideProgressBar()
            }
        }
    }

    private fun checkIsFavorite(){

    }
}