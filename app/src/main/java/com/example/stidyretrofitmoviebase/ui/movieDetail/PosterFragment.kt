package com.example.stidyretrofitmoviebase.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.stidyretrofitmoviebase.databinding.FragmentPosterBinding
import com.example.stidyretrofitmoviebase.presentation.model.PosterState
import com.example.stidyretrofitmoviebase.presentation.movieDetail.PosterViewModel

class PosterFragment : Fragment() {

    private var _binding: FragmentPosterBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: PosterViewModel

    private lateinit var posterUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        posterUrl = requireArguments().getString(POSTER_URL) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, PosterViewModel.getViewModelFactory(posterUrl)
        )[PosterViewModel::class.java]

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: PosterState) {
        when (state) {
            is PosterState.Success -> {
                showContent(state.url)
            }
        }
    }

    private fun showContent(url: String) {
        Glide.with(this).load(url).into(binding.poster)
    }

    companion object {
        const val POSTER_URL = "poster_url"

        fun newInstance(posterUrl: String): PosterFragment {
            return PosterFragment().apply { arguments = bundleOf(POSTER_URL to posterUrl) }
        }
    }

}