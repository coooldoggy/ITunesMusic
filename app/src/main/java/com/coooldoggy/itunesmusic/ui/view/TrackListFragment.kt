package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.FragmentTracklistBinding
import com.coooldoggy.itunesmusic.databinding.LayoutLoadingErrorBinding
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseFragment
import com.coooldoggy.itunesmusic.ui.viewmodel.FavoriteViewModel
import com.coooldoggy.itunesmusic.ui.viewmodel.TrackListViewModel
import kotlinx.coroutines.launch

class TrackListFragment : BaseFragment(){
    private lateinit var viewDataBinding: FragmentTracklistBinding
    private lateinit var errorBinding: LayoutLoadingErrorBinding
    private val viewModel by viewModels<TrackListViewModel>()
    private val favViewModel by activityViewModels<FavoriteViewModel>()
    private lateinit var trackListAdapter: TrackListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<FragmentTracklistBinding>(inflater, R.layout.fragment_tracklist, container, false).apply {
            lifecycleOwner = this@TrackListFragment
            model = viewModel
        }
        errorBinding = viewDataBinding.vsError
        errorBinding.rlRetry.setOnClickListener {
            viewModel.loadTrackList()
        }
        observeViewModelEvent(this, viewModel)
        observeViewModelEvent(viewLifecycleOwner, favViewModel)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackListAdapter = TrackListAdapter(viewModel.trackList, favViewModel.favList).apply {
            starClick = object : TrackListAdapter.StarClick{
                override fun onClick(song: Song) {
                    Log.d(TAG, "$song")
                    lifecycleScope.launch {
                        val isFavorite = favViewModel.isFavorite(song).await()
                        if (isFavorite){
                            favViewModel.deleteSong(song)
                        }else{
                            favViewModel.insertSong(song)
                        }
                    }
                }
            }
        }
        viewDataBinding.rvTrack.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = trackListAdapter
            addOnScrollListener(rvScrollListener)
        }
        viewModel.loadTrackList()
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val manager = recyclerView.layoutManager as LinearLayoutManager
            if (manager.itemCount <= manager.findLastVisibleItemPosition() + 2){
                viewModel.loadTrackList()
            }
        }
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        when(eventId){
            TrackListViewModel.EVENT_TRACK_LOADED -> {
                errorBinding.root.visibility = View.GONE
                viewDataBinding.rvTrack.adapter?.notifyDataSetChanged()
            }
            TrackListViewModel.EVENT_TRACK_LOAD_FAIL -> {
                viewDataBinding.vsError.root.visibility = View.VISIBLE
            }
            TrackListViewModel.EVENT_TRACK_FAV_DELETED -> {
                viewDataBinding.rvTrack.adapter?.notifyDataSetChanged()
            }
        }
    }
}