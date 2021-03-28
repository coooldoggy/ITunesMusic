package com.coooldoggy.itunesmusic.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.databinding.ItemFavoriteBinding
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.viewmodel.TrackListViewModel

class FavoriteListAdapter(var favoriteList: ArrayList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var starClick: StarClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteListItemHolder.from(parent)
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
    lateinit var starIcon: ImageView

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemFavoriteBinding.inflate(layoutInflater, parent, false)
            return FavoriteListItemHolder(binding)
        }
    }

    fun bind(song: Song) {
        binding.model = song
        starIcon = binding.ivFavorite
        binding.executePendingBindings()
    }
}