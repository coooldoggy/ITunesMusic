package com.coooldoggy.itunesmusic.framework.data

import com.google.gson.annotations.SerializedName

data class SongSearchResult (
    @SerializedName("resultCount")
    val resultCount: Int,

    @SerializedName("results")
    val results: ArrayList<Song>
)