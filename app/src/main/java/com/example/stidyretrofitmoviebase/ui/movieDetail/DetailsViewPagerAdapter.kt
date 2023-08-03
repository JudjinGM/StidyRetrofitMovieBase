package com.example.stidyretrofitmoviebase.ui.movieDetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: androidx.lifecycle.Lifecycle,
    private val posterUrl: String,
    private val posterId: String,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) PosterFragment.newInstance(posterUrl) else
            AboutFragment.newInstance(posterId)
    }


}