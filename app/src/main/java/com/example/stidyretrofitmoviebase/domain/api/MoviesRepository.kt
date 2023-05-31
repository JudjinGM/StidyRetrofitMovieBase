package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}