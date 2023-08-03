package com.example.stidyretrofitmoviebase.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.stidyretrofitmoviebase.databinding.FragmentDetailsBinding
import com.example.stidyretrofitmoviebase.ui.movies.MoviesFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var posterUrl: String
    private lateinit var posterId: String

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        posterUrl = requireArguments().getString(POSTER_URL) ?: ""
        posterId = requireArguments().getString(MOVIE_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            posterUrl = posterUrl,
            posterId = posterId
        )

        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                if (position == 0) tab.text = "Постер"
                else tab.text = "О фильме"
            }
        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator.detach()
    }

    companion object {
        private const val POSTER_URL = "poster_url"
        private const val MOVIE_ID = "movie id"

        const val TAG = "Details fragment"

        fun createArgs(moveId: String, posterUrl: String) =
            bundleOf(MoviesFragment.MOVIE_ID to moveId, MoviesFragment.POSTER_URL to posterUrl)
    }
}


