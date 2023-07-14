package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.domain.models.Names

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    fun getMovieDetail(id: String, consumer: MoviesDetailConsumer)
    fun getMovieFullCast(id: String, consumer: MovieCastConsumer)

    fun searchNames(namesQuery: String, consumer: NamesConsummer)
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

    interface NamesConsummer {
        fun consume(names: List<Names>?, errorMessage: String?)
    }
}