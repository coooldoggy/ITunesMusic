package com.coooldoggy.itunesmusic.ui.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.coooldoggy.itunesmusic.framework.data.*
import com.coooldoggy.itunesmusic.ui.common.BaseViewModel
import com.coooldoggy.itunesmusic.ui.common.BaseViewModelEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val EVENT_FAVLIST_LOADED = 2000
        const val EVENT_FAVLIST_ITEM_DELETED = EVENT_FAVLIST_LOADED + 1
    }
    private val favoriteSongDao: FavoriteSongDao =
        FavoriteSongDB.getFavDB(application.applicationContext).favSongDao()

    var favList = arrayListOf<Song>()

    fun insertSong(song: Song) {
        val songEntity = FavoriteSongEntity(
            wrapperType = song.wrapperType,
            kind = song.kind,
            artistId = song.artistId,
            collectionId = song.collectionId,
            trackId = song.trackId,
            artistName = song.artistName,
            collectionName = song.collectionName,
            trackName = song.trackName,
            artworkUrl60 = song.artworkUrl60
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favoriteSongDao.insertFavoriteSong(songEntity)
                getAllFavoriteSong()
            }
        }
    }

    fun deleteSong(song: Song) {
        val index = favList.indexOf(song)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favoriteSongDao.deleteFavoriteSong(song.trackId)
            }
            favList.remove(song)
            sendToEvent.value = BaseViewModelEvent.Event(EVENT_FAVLIST_ITEM_DELETED, index)
        }
    }

    fun getAllFavoriteSong() {
        favList.clear()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val dbResult = favoriteSongDao.getAllFavoriteSong()
                dbResult.forEach { favSong ->
                    val song = Song(
                        wrapperType = favSong.wrapperType,
                        kind = favSong.kind,
                        artistId = favSong.artistId,
                        collectionId = favSong.collectionId,
                        trackId = favSong.trackId,
                        artistName = favSong.artistName,
                        collectionName = favSong.collectionName,
                        trackName = favSong.trackName,
                        artworkUrl60 = favSong.artworkUrl60
                    )
                    favList.add(song)
                }
            }
            sendToEvent.value = BaseViewModelEvent.Event(EVENT_FAVLIST_LOADED, "")
        }
    }

    fun isFavorite(song: Song) = viewModelScope.async {
        withContext(Dispatchers.IO){
            val result = favoriteSongDao.isRowIsExist(song.trackId)
            result
        }
    }

}