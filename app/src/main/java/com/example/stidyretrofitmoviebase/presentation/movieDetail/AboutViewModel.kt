package com.example.stidyretrofitmoviebase.presentation.movieDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.presentation.model.AboutState
import com.example.stidyretrofitmoviebase.utill.Creator

class AboutViewModel(private val movieId: String, private val moviesInteractor: MoviesInteractor) :
    ViewModel() {
    private val stateLiveData = MutableLiveData<AboutState>()

    init {
        getDetails()
    }

    fun observeState(): LiveData<AboutState> = stateLiveData

    fun postState(state: AboutState) {
        stateLiveData.postValue(state)
    }

    private fun getDetails() {
        if (movieId.isEmpty()) {
            postState(AboutState.Error("Something went wrong"))
            return
        }
        moviesInteractor.getMovieDetail(movieId, object : MoviesInteractor.MoviesDetailConsumer {
            override fun consume(movieDetail: MovieDetail?, errorMessage: String?) {
                if (errorMessage != null) {
                    postState(AboutState.Error(errorMessage))
                } else if (movieDetail != null) {
                    postState(AboutState.Success(movieDetail))
                }
            }
        })
    }

    companion object {
        fun getViewModelFactory(movieId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AboutViewModel(
                    movieId,
                    moviesInteractor = Creator.provideMoviesInteractor(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                )
            }
        }
    }
}