package com.example.stidyretrofitmoviebase.ui.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.databinding.FragmentMoviesBinding
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.presentation.movies.MoviesSearchViewModel
import com.example.stidyretrofitmoviebase.presentation.movies.models.MoviesState
import com.example.stidyretrofitmoviebase.ui.movieDetail.DetailsFragment
import com.example.stidyretrofitmoviebase.ui.movies.models.ToastState
import com.example.stidyretrofitmoviebase.ui.root.RootActivity
import com.example.stidyretrofitmoviebase.utill.debounce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : androidx.fragment.app.Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var onMovieClickDebounce: (Movie) -> Unit

    private val viewModel: MoviesSearchViewModel by viewModel()

    private var adapter: MoviesAdapter? = null

    private var textWatcher: TextWatcher? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onMovieClickDebounce = debounce<Movie>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { movie ->
            findNavController().navigate(
                R.id.action_moviesFragment_to_detailsFragment,
                DetailsFragment.createArgs(movie.id, movie.image)
            )
        }

        adapter = MoviesAdapter(object : MoviesAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                onMovieClickDebounce(movie)
                (activity as RootActivity).animateBottomNavigationView()
            }

            override fun onFavoriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }
        })

        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchDebounce(
                    changedText = p0?.toString() ?: ""
                )
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        textWatcher?.let { binding.queryInput.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeToastState().observe(viewLifecycleOwner) { toastState ->
            if (toastState is ToastState.Show) {
                showToast(toastState.additionalMessage)
                viewModel.toastWasShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
        adapter = null
        binding.moviesList.adapter = null

    }

    private fun showLoading() {
        binding.moviesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.moviesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(movies: List<Movie>) {
        binding.moviesList.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter?.movies?.clear()
        adapter?.movies?.addAll(movies)
        adapter?.notifyDataSetChanged()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
            MoviesState.Loading -> showLoading()
        }
    }

    private fun showToast(additionalMessage: String) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        const val MOVIE_ID = "movie id"
        const val POSTER_URL = "poster_url"
    }
}
