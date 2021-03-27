package com.coooldoggy.itunesmusic.ui.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.coooldoggy.itunesmusic.framework.data.*
import com.coooldoggy.itunesmusic.ui.common.BaseViewModel
import com.coooldoggy.itunesmusic.ui.common.BaseViewModelEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val EVENT_FAVLIST_LOADED = 1000
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
        val songEntity = song as FavoriteSongEntity
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favoriteSongDao.deleteFavoriteSong(songEntity)
            }
        }
    }

    fun getAllFavoriteSong() {
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

}