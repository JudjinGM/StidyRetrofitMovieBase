package com.example.stidyretrofitmoviebase.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.databinding.FragmentAboutBinding
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.presentation.movieDetail.AboutViewModel
import com.example.stidyretrofitmoviebase.presentation.movieDetail.models.AboutState
import com.example.stidyretrofitmoviebase.ui.movieCast.MovieCastFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!
    private lateinit var movieId: String

    private val viewModel: AboutViewModel by viewModel {
        parametersOf(movieId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = requireArguments().getString(MOVIE_ID) ?: ""

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        setOnClicks()
    }

    private fun setOnClicks() {
        binding.showCastButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailsFragment_to_movieCastFragment,
                MovieCastFragment.createArgs(movieId)
            )

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
        const val MOVIE_ID = "movie id"
        fun newInstance(movieId: String): AboutFragment {
            return AboutFragment().apply {
                arguments = bundleOf(MOVIE_ID to movieId)
            }
        }
    }
}