package com.example.stidyretrofitmoviebase.di

import com.example.stidyretrofitmoviebase.presentation.movieCast.MovieCastViewModel
import com.example.stidyretrofitmoviebase.presentation.movieDetail.AboutViewModel
import com.example.stidyretrofitmoviebase.presentation.movieDetail.PosterViewModel
import com.example.stidyretrofitmoviebase.presentation.movies.MoviesSearchViewModel
import com.example.stidyretrofitmoviebase.presentation.names.NamesSearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        MoviesSearchViewModel(
            moviesInteractor = get(), context = androidApplication()
        )
    }

    viewModel { (posterUrl: String) ->
        PosterViewModel(posterUrl = posterUrl)
    }

    viewModel { (movieId: String) ->
        AboutViewModel(movieId = movieId, moviesInteractor = get())
    }

    viewModel { (movieId: String) ->
        MovieCastViewModel(movieId = movieId, interactor = get())
    }

    viewModel {
        NamesSearchViewModel(moviesInteractor = get(), context = androidApplication())
    }
}