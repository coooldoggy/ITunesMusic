package com.coooldoggy.itunesmusic.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindAdapters {
    private val TAG = BindAdapters::class.java.simpleName


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }
}