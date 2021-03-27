package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.FragmentFavoriteBinding
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseFragment
import com.coooldoggy.itunesmusic.ui.viewmodel.FavoriteViewModel

class FavoriteListFragment : BaseFragment(){
    private lateinit var viewDataBinding: FragmentFavoriteBinding
    private val favViewModel by activityViewModels<FavoriteViewModel>()
    private lateinit var favListAdapter: FavoriteListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<FragmentFavoriteBinding>(inflater, R.layout.fragment_favorite, container, false).apply {
            lifecycleOwner = this@FavoriteListFragment
        }
        observeViewModelEvent(this, favViewModel)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favListAdapter = FavoriteListAdapter(favViewModel.favList).apply {
            starClick = object : FavoriteListAdapter.StarClick{
                override fun onClick(song: Song) {
                    favViewModel.deleteSong(song)
                }
            }
        }
        viewDataBinding.rvFavorite.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = favListAdapter
        }
        favViewModel.getAllFavoriteSong()
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        when(eventId){
            FavoriteViewModel.EVENT_FAVLIST_LOADED -> {
                viewDataBinding.rvFavorite.adapter?.notifyDataSetChanged()
            }
        }
    }

}