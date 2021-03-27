package com.coooldoggy.itunesmusic.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.databinding.ItemTrackBinding
import com.coooldoggy.itunesmusic.framework.data.Song

class TrackListAdapter(var tracklist: ArrayList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrackListItemHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return tracklist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = tracklist[position]
        (holder as TrackListItemHolder).bind(song)
    }

    class TrackListItemHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object{
            fun from(parent: ViewGroup): RecyclerView.ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTrackBinding.inflate(layoutInflater, parent, false)
                return TrackListItemHolder(binding)
            }
        }

        fun bind(song: Song){
            binding.model = song
            binding.executePendingBindings()
        }
    }

}