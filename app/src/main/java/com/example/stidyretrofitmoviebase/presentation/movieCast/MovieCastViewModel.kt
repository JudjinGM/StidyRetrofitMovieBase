package com.example.stidyretrofitmoviebase.presentation.movieCast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.presentation.movieCast.models.MovieCastState
import com.example.stidyretrofitmoviebase.ui.movieCast.MoviesCastRVItem

class MovieCastViewModel(
    private val movieId: String, private val interactor: MoviesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MovieCastState>()

    init {
        postState(MovieCastState.Loading)

        interactor.getMovieFullCast(movieId, object : MoviesInteractor.MovieCastConsumer {
            override fun consume(movieCast: MovieCast?, errorMessage: String?) {
                if (movieCast != null) {
                    postState(castUiStateToContent(movieCast))
                } else {
                    postState(MovieCastState.Error(errorMessage ?: "Unknown Error"))
                }
            }
        })
    }

    private fun castUiStateToContent(cast: MovieCast): MovieCastState {
        val items = buildList<MoviesCastRVItem> {
            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }
        return MovieCastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }

    fun observeState(): LiveData<MovieCastState> = stateLiveData
    fun postState(state: MovieCastState) {
        stateLiveData.postValue(state)
    }

}