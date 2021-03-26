package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coooldoggy.itunesmusic.databinding.FragmentFavoriteBinding
import com.coooldoggy.itunesmusic.ui.common.BaseFragment

class FavoriteListFragment : BaseFragment(){
    private var _viewBinding : FragmentFavoriteBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        TODO("Not yet implemented")
    }

}