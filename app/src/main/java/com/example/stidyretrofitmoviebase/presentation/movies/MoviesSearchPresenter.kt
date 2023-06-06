package com.example.stidyretrofitmoviebase.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.ui.movies.models.MoviesState
import com.example.stidyretrofitmoviebase.utill.Creator
import moxy.MvpPresenter

class MoviesSearchPresenter(
    private val context: Context,
) : MvpPresenter<MoviesView>() {
    private val moviesInteractor = Creator.provideMoviesInteractor(context)

    private var latestSearchText: String? = null

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val movies = ArrayList<Movie>()

    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    private fun searchRequest(newSearchText: String) {
        if (this.latestSearchText == newSearchText) {
            return
        }
        this.lastSearchText = newSearchText

        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                renderState(MoviesState.Error(context.getString(R.string.something_went_wrong)))
                                viewState.showToast(errorMessage)
                            }
                            movies.isEmpty() -> {
                                renderState(MoviesState.Empty(context.getString(R.string.nothing_found)))
                            }

                            else -> {
                                renderState(MoviesState.Content(movies))
                            }
                        }
                    }
                }
            })
        }
    }

    fun searchDebounce(changedText: String) {
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    private fun renderState(state: MoviesState) {
        viewState.render(state)
    }
}