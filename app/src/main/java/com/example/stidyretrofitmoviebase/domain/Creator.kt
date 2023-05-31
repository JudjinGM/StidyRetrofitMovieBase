package com.example.stidyretrofitmoviebase.domain

import android.app.Activity
import com.example.stidyretrofitmoviebase.data.MoviesRepositoryImpl
import com.example.stidyretrofitmoviebase.data.network.RetrofitNetworkClient
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.impl.MoviesInteractorImpl
import com.example.stidyretrofitmoviebase.presentation.MoviesSearchController
import com.example.stidyretrofitmoviebase.presentation.PosterController
import com.example.stidyretrofitmoviebase.ui.moves.MoviesAdapter

object Creator {

    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController{
        return PosterController(activity)
    }
}
