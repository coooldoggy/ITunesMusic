package com.coooldoggy.itunesmusic.framework.data

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("wrapperType")
    val wrapperType: String,

    @SerializedName("kind")
    val kind: String,

    @SerializedName("artistId")
    val artistId: Int,

    @SerializedName("collectionId")
    val collectionId: Int,

    @SerializedName("trackId")
    val trackId: Int,

    @SerializedName("collectionName")
    val collectionName: String,

    @SerializedName("trackName")
    val trackName: String,

    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String,

    @SerializedName("artistViewUrl")
    val artistViewUrl: String,

    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String,

    @SerializedName("trackViewUrl")
    val trackViewUrl: String,

    @SerializedName("previewUrl")
    val previewUrl: String,

    @SerializedName("artworkUrl30")
    val artworkUrl30: String,

    @SerializedName("artworkUrl60")
    val artworkUrl60: String,

    @SerializedName("artworkUrl100")
    val artworkUrl100: String,

    @SerializedName("collectionPrice")
    val collectionPrice: Float,

    @SerializedName("trackPrice")
    val trackPrice: Float,

    @SerializedName("releaseDate")
    val releaseDate: String,

    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String,

    @SerializedName("trackExplicitness")
    val trackExplicitness: String,

    @SerializedName("discCount")
    val discCount: Int,

    @SerializedName("discNumber")
    val discNumber: Int,

    @SerializedName("trackCount")
    val trackCount: Int,

    @SerializedName("trackNumber")
    val trackNumber: Int,

    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("primaryGenreName")
    val primaryGenreName: String,

    @SerializedName("isStreamable")
    val isStreamable: Boolean

)