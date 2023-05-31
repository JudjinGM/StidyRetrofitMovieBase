package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.utill.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}