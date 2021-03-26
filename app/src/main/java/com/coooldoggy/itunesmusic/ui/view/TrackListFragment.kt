package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.FragmentTracklistBinding
import com.coooldoggy.itunesmusic.databinding.ItemTrackBinding
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseFragment
import com.coooldoggy.itunesmusic.ui.viewmodel.TrackListViewModel
import kotlinx.coroutines.launch

class TrackListFragment : BaseFragment(){
    private lateinit var viewDataBinding: FragmentTracklistBinding
    private val viewModel by viewModels<TrackListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<FragmentTracklistBinding>(inflater, R.layout.fragment_tracklist, container, false).apply {
            lifecycleOwner = this@TrackListFragment
        }
        observeViewModelEvent(this, viewModel)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.rvTrack.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = TrackListAdapter(viewModel.trackList)
        }
        viewModel.loadTrackList()
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        when(eventId){
            TrackListViewModel.EVENT_TRACK_LOADED -> {
                viewDataBinding.rvTrack.adapter?.notifyDataSetChanged()
            }
        }
    }

    class TrackListAdapter(var tracklist: ArrayList<Song>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        lateinit var itemViewBinding: ItemTrackBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            itemViewBinding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TrackListItemHolder(itemViewBinding.root)
        }

        override fun getItemCount(): Int {
            return tracklist.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        }

        class TrackListItemHolder(view: View): RecyclerView.ViewHolder(view){

        }

    }

}