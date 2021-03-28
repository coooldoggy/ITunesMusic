package com.coooldoggy.itunesmusic.framework.data

import androidx.room.*

@Dao
interface FavoriteSongDao {
    @Query("SELECT * FROM FavoriteSongEntity")
    fun getAllFavoriteSong(): List<FavoriteSongEntity>

    @Query("SELECT * FROM FavoriteSongEntity WHERE songId = (:songId)")
    fun getFavoriteSongFromID(songId: Int): FavoriteSongEntity

    @Query("SELECT EXISTS(SELECT * FROM FavoriteSongEntity WHERE trackId = :trackId)")
    fun isRowIsExist(trackId : Int) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteSong(favoriteSongEntity: FavoriteSongEntity)

    @Query("DELETE FROM FavoriteSongEntity WHERE collectionId=:trackId")
    fun deleteFavoriteSong(trackId: Int)

}