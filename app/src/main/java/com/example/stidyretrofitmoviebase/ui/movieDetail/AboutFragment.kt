package com.example.stidyretrofitmoviebase.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stidyretrofitmoviebase.databinding.FragmentDetailsBinding
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.presentation.model.AboutState
import com.example.stidyretrofitmoviebase.presentation.movieDetail.AboutViewModel

class AboutFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AboutViewModel

    private lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getString(MOVIE_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, AboutViewModel.getViewModelFactory(movieId)
        )[AboutViewModel::class.java]

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(aboutState: AboutState) {
        when (aboutState) {
            is AboutState.Success -> showContent(aboutState.movieDetail)
            is AboutState.Error -> showError(aboutState.message)
        }
    }

    private fun showContent(movieDetails: MovieDetail) {
        binding.details.isVisible = true
        binding.errorMessage.isVisible = false

        binding.title.text = movieDetails.title
        binding.ratingValue.text = movieDetails.imDbRating
        binding.yearValue.text = movieDetails.year
        binding.countryValue.text = movieDetails.countries
        binding.genreValue.text = movieDetails.genres
        binding.directorValue.text = movieDetails.directors
        binding.writerValue.text = movieDetails.writers
        binding.castValue.text = movieDetails.stars
        binding.plot.text = movieDetails.plot
    }

    private fun showError(errorMessage: String) {
        binding.details.isVisible = false
        binding.errorMessage.isVisible = true
        binding.errorMessage.text = errorMessage
    }


    companion object {
        const val MOVIE_ID = "id"
        fun newInstance(movieId: String): AboutFragment {
            return AboutFragment().apply {
                arguments = bundleOf(MOVIE_ID to movieId)
            }
        }
    }
}