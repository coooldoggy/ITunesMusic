package com.coooldoggy.itunesmusic.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.ItemFavoriteBinding
import com.coooldoggy.itunesmusic.framework.data.Song

class FavoriteListAdapter(var favoriteList: ArrayList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var starClick: StarClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil.inflate<ItemFavoriteBinding>(layoutInflater, R.layout.item_favorite, parent,false)
        return FavoriteListItemHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = favoriteList[position]
        (holder as FavoriteListItemHolder).bind(song)
        holder.starIcon.setOnClickListener {
            holder.starIcon.isSelected = !holder.starIcon.isSelected
            starClick?.onClick(song, position)
        }
        holder.starIcon.isSelected = true
    }

    interface StarClick {
        fun onClick(song: Song, position: Int)
    }
}

class FavoriteListItemHolder(private val binding: ItemFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
     var starIcon: ImageView = binding.ivFavorite

    fun bind(song: Song) {
        binding.model = song
//        binding.executePendingBindings()
    }
}