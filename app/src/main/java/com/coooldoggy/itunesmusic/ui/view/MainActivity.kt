package com.coooldoggy.itunesmusic.ui.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coooldoggy.itunesmusic.R
import com.coooldoggy.itunesmusic.databinding.ActivityMainBinding
import com.coooldoggy.itunesmusic.framework.api.ApiManager
import com.coooldoggy.itunesmusic.ui.common.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {
    private lateinit var viewDataBinding: ActivityMainBinding

    enum class FragmentEnum(internal var tag: String){
        TRACK("trackListFragment"),
        FAVORITE("favoriteListFragment")
    }

    private val trackListFragment: TrackListFragment by lazy {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentEnum.TRACK.tag)
        return@lazy fragment as? TrackListFragment
            ?: TrackListFragment()
    }

    private val favoriteListFragment: FavoriteListFragment by lazy {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentEnum.FAVORITE.tag)
        return@lazy fragment as? FavoriteListFragment
            ?: FavoriteListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewDataBinding.let {
            it.lifecycleOwner = this
        }
        setView()
    }

    private fun setView(){
        setFragmentID(R.id.fragment_container)
        viewDataBinding.viewpager.apply {
            offscreenPageLimit = 2
            adapter = FragmentPageAdapter(this@MainActivity)
            currentItem = 0
        }
        viewDataBinding.bottomNav.setOnNavigationItemSelectedListener(bottomNavClickListener)
    }

    private val bottomNavClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragmentEnum = FragmentEnum.TRACK

        when(item.itemId){
            R.id.bottom_track -> {
                fragmentEnum = FragmentEnum.TRACK
                changeFragment(fragmentEnum)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_favorite -> {
                fragmentEnum = FragmentEnum.FAVORITE
                changeFragment(fragmentEnum)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                changeFragment(fragmentEnum)
                return@OnNavigationItemSelectedListener true
            }
        }
    }


    private fun changeFragment(fragmentEnum: FragmentEnum){
        when(fragmentEnum){
            FragmentEnum.TRACK -> {
                viewDataBinding.viewpager.setCurrentItem(0, false)
            }
            FragmentEnum.FAVORITE -> {
                viewDataBinding.viewpager.setCurrentItem(1, false)
            }
        }
    }

    override fun onViewModelEvent(eventId: Int, param: Any) {
    }

    inner class FragmentPageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){

        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> trackListFragment
                1 -> favoriteListFragment
                else -> Fragment()
            }
        }
    }

}