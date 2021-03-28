package com.coooldoggy.itunesmusic.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.databinding.ItemTrackBinding
import com.coooldoggy.itunesmusic.framework.data.Song

class TrackListAdapter(var tracklist: ArrayList<Song>, var favoriteList: ArrayList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var starClick: StarClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrackListItemHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return tracklist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = tracklist[position]
        var isFavorite = false
        favoriteList.forEach {favSong ->
            if (favSong.trackId == song.trackId){
                isFavorite = true
            }
        }
        (holder as TrackListItemHolder).bind(song, isFavorite)
        holder.starIcon.setOnClickListener {
            holder.starIcon.isSelected = !holder.starIcon.isSelected
            starClick?.onClick(song)
        }
    }

    interface StarClick {
        fun onClick(song: Song)
    }
}

class TrackListItemHolder(private val binding: ItemTrackBinding) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var starIcon: ImageView

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTrackBinding.inflate(layoutInflater, parent, false)
            return TrackListItemHolder(binding)
        }
    }

    fun bind(song: Song, isFavorite: Boolean) {
        binding.model = song
        binding.isFavorite = isFavorite
        starIcon = binding.ivFavorite
        binding.executePendingBindings()
    }
}
