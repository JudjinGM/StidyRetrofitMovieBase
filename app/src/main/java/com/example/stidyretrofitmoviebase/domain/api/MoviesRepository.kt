package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.utill.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>

    fun addMovieToFavorites(movies: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}