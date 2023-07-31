package com.example.stidyretrofitmoviebase.domain.api

import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.utill.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun getDetails(id: String): Flow<Resource<MovieDetail>>
    fun getMovieCast(id: String): Flow<Resource<MovieCast>>
    fun getNames(namesQuery: String): Flow<Resource<List<Names>>>
    fun addMovieToFavorites(movies: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}