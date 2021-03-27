package com.coooldoggy.itunesmusic.framework.data

import androidx.room.*

@Dao
interface FavoriteSongDao {
    @Query("SELECT * FROM FavoriteSongEntity")
    fun getAllFavoriteSong(): List<FavoriteSongEntity>

    @Query("SELECT * FROM FavoriteSongEntity WHERE songId = (:songId)")
    fun getFavoriteSongFromID(songId: Int): FavoriteSongEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteSong(favoriteSongEntity: FavoriteSongEntity)

    @Delete
    fun deleteFavoriteSong(favoriteSongEntity: FavoriteSongEntity)
}