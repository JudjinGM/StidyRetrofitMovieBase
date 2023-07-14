package com.example.stidyretrofitmoviebase.ui.movieCast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stidyretrofitmoviebase.databinding.FragmentMoviesCastBinding
import com.example.stidyretrofitmoviebase.presentation.movieCast.MovieCastViewModel
import com.example.stidyretrofitmoviebase.presentation.movieCast.models.MovieCastState
import com.example.stidyretrofitmoviebase.ui.movies.MoviesFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCastFragment : Fragment() {

    private var _binding: FragmentMoviesCastBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieId: String

    private val viewModel: MovieCastViewModel by viewModel {
        parametersOf(movieId)
    }

    private val adapter = ListDelegationAdapter(
        movieCastHeaderDelegate(),
        movieCastPersonDelegate(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getString(MOVIE_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: MovieCastState) {
        when (state) {
            is MovieCastState.Loading -> showLoading()
            is MovieCastState.Content -> showContent(state)
            is MovieCastState.Error -> showError(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.errorMessageTextView.isVisible = false
        binding.contentContainer.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = true
        binding.contentContainer.isVisible = false

        binding.errorMessageTextView.text = message
    }

    private fun showContent(state: MovieCastState.Content) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = false
        binding.contentContainer.isVisible = true

        binding.movieTitle.text = state.fullTitle

        adapter.items = state.items
        adapter.notifyDataSetChanged()
    }

    companion object {

        private const val MOVIE_ID = "movie id"

        fun createArgs(moveId: String) =
            bundleOf(MoviesFragment.MOVIE_ID to moveId)
    }
}