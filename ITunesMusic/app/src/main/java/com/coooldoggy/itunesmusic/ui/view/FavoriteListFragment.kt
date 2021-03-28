package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.itunesmusic.databinding.FragmentFavoriteBinding
import com.coooldoggy.itunesmusic.framework.data.Song
import com.coooldoggy.itunesmusic.ui.common.BaseFragment
import com.coooldoggy.itunesmusic.ui.viewmodel.FavoriteViewModel

class FavoriteListFragment : BaseFragment(){
    private lateinit var viewDataBinding: FragmentFavoriteBinding
    private val favViewModel by activityViewModels<FavoriteViewModel>()
    private lateinit var favListAdapter: FavoriteListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        observeViewModelEvent(viewLifecycleOwner, favViewModel)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favListAdapter = FavoriteListAdapter(favViewModel.favList).apply {
            starClick = object : FavoriteListAdapter.StarClick{
                override fun onClick(song: Song, position: Int) {
                    favViewModel.deleteSong(song)
                }
            }
        }
        viewDataBinding.rvFavorite.apply{
            layoutManager = LinearLayoutManager(context).apply {
                setHasFixedSize(true)
                addOnScrollListener(rvScrollListener)
            }
            adapter = favListAdapter
        }
        favViewModel.getAllFavoriteSong()
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            recyclerView.scrollTo(dx, dy)
        }
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
        when(eventId){
            FavoriteViewModel.EVENT_FAVLIST_LOADED -> {
                viewDataBinding.rvFavorite.adapter?.notifyDataSetChanged()
            }
            FavoriteViewModel.EVENT_FAVLIST_ITEM_DELETED -> {
                favViewModel.getAllFavoriteSong()
            }
        }
    }

}