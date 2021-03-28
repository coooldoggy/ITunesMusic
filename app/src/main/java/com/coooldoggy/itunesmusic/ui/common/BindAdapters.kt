package com.coooldoggy.itunesmusic.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.coooldoggy.itunesmusic.framework.data.Song

object BindAdapters {
    private val TAG = BindAdapters::class.java.simpleName


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: View, isSelected: Boolean?) {
        isSelected?.let {
            view.isSelected = it
        }
    }
}