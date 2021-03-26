package com.coooldoggy.itunesmusic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coooldoggy.itunesmusic.databinding.FragmentTracklistBinding
import com.coooldoggy.itunesmusic.ui.common.BaseFragment

class TrackListFragment : BaseFragment(){
    private var _viewBinding : FragmentTracklistBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentTracklistBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        TODO("Not yet implemented")
    }

}