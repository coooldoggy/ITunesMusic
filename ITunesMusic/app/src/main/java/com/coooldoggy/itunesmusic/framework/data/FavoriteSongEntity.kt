package com.coooldoggy.itunesmusic.framework.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteSongEntity(
    @PrimaryKey(autoGenerate = true) val songId: Int = 0,
    @ColumnInfo(name = "wrapperType") val wrapperType: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "artistId") val artistId: Int,
    @ColumnInfo(name = "collectionId") val collectionId: Int,
    @ColumnInfo(name = "trackId") val trackId: Int,
    @ColumnInfo(name = "artistName") val artistName: String,
    @ColumnInfo(name = "collectionName") val collectionName: String,
    @ColumnInfo(name = "trackName") val trackName: String,
    @ColumnInfo(name = "artworkUrl60") val artworkUrl60: String
)