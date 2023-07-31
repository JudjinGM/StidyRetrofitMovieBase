package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.domain.models.Names
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {

    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>
    fun getMovieDetail(id: String): Flow<Pair<MovieDetail?, String?>>
    fun getMovieFullCast(id: String): Flow<Pair<MovieCast?, String?>>

    fun searchNames(namesQuery: String): Flow<Pair<List<Names>?, String?>>
    fun addMoviesToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface MoviesDetailConsumer {
        fun consume(movieDetail: MovieDetail?, errorMessage: String?)
    }

    interface MovieCastConsumer {
        fun consume(movieCast: MovieCast?, errorMessage: String?)
    }

    interface NamesConsumer {
        fun consume(names: List<Names>?, errorMessage: String?)
    }
}