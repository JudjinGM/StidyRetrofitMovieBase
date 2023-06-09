package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    fun addMoviesToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

    interface MoviesConsumer{
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }
}