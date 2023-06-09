package com.example.stidyretrofitmoviebase.utill

import LocalStorage
import android.content.Context
import com.example.stidyretrofitmoviebase.data.MoviesRepositoryImpl
import com.example.stidyretrofitmoviebase.data.network.RetrofitNetworkClient
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.impl.MoviesInteractorImpl
import com.example.stidyretrofitmoviebase.presentation.poster.PosterPresenter
import com.example.stidyretrofitmoviebase.presentation.poster.PosterView

object Creator {

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(
            RetrofitNetworkClient(context),
            localStorage = LocalStorage(
                context.getSharedPreferences(
                    "local_storage",
                    Context.MODE_PRIVATE
                )
            )
        )
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }


    fun providePosterController(view: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(view, imageUrl)
    }
}
