package com.example.stidyretrofitmoviebase.utill

import android.app.Activity
import android.content.Context
import com.example.stidyretrofitmoviebase.data.MoviesRepositoryImpl
import com.example.stidyretrofitmoviebase.data.network.RetrofitNetworkClient
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.impl.MoviesInteractorImpl
import com.example.stidyretrofitmoviebase.presentation.MoviesSearchController
import com.example.stidyretrofitmoviebase.presentation.PosterController
import com.example.stidyretrofitmoviebase.ui.moves.MoviesAdapter

object Creator {

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController{
        return PosterController(activity)
    }
}
