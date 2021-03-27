package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.FragmentTracklistBinding
import com.coooldoggy.itunesmusic.ui.common.BaseFragment
import com.coooldoggy.itunesmusic.ui.viewmodel.TrackListViewModel

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
            layoutManager = LinearLayoutManager(context)
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
}