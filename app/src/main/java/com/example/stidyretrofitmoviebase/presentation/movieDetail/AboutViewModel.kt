package com.example.stidyretrofitmoviebase.presentation.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.presentation.movieDetail.models.AboutState
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            moviesInteractor.getMovieDetail(movieId).collect { pairs ->
                processResult(pairs.first, pairs.second)
            }
        }
    }

    private fun processResult(movieDetail: MovieDetail?, errorMessage: String?) {
        if (errorMessage != null) {
            postState(AboutState.Error(errorMessage))
        }
        if (movieDetail != null) {
            postState(AboutState.Success(movieDetail))
        }
    }
}