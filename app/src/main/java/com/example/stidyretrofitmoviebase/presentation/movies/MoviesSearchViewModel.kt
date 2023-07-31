package com.example.stidyretrofitmoviebase.presentation.movies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.presentation.movies.models.MoviesState
import com.example.stidyretrofitmoviebase.presentation.names.models.NamesState
import com.example.stidyretrofitmoviebase.ui.movies.models.ToastState
import com.example.stidyretrofitmoviebase.utill.debounce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MoviesSearchViewModel(
    private val moviesInteractor: MoviesInteractor, private val context: Context
) : ViewModel() {


    private val toastState = MutableLiveData<ToastState>(ToastState.None)
    fun observeToastState(): LiveData<ToastState> = toastState

    private val stateLiveData = MutableLiveData<MoviesState>()

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                MoviesState.Loading -> movieState
            }
        }
    }

    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    private var latestSearchText: String? = null

    private val searchMovieDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {
            searchRequest(it)
        }


    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        searchMovieDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            postState(MoviesState.Loading)

            viewModelScope.launch {
                moviesInteractor.searchMovies(newSearchText).collect{pairs->
                    processResult(pairs.first, pairs.second)
                }
            }
        }
    }

    private fun processResult(foundMovies: List<Movie>?, errorMessage: String?) {
        val movies = mutableListOf<Movie>()
        if (foundMovies != null) {
            movies.addAll(foundMovies)
        }
        when {
            errorMessage != null -> {
                postState(MoviesState.Error(errorMessage))
                showToast(errorMessage)
            }

            movies.isEmpty() -> {
                postState(MoviesState.Empty(R.string.nothing_found.toString()))
            }

            else -> postState(MoviesState.Content(movies))
        }
    }

    private fun postState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    private fun showToast(message: String) {
        toastState.postValue(ToastState.Show(message))
    }

    fun toastWasShown() {
        toastState.value = ToastState.None
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMoviesToFavorites(movie)
        }

        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        if (currentState is MoviesState.Content) {
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            if (movieIndex != -1) {
                stateLiveData.value = MoviesState.Content(currentState.movies.toMutableList().also {
                    it[movieIndex] = newMovie
                })
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}