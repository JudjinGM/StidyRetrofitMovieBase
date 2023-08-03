package com.example.stidyretrofitmoviebase.domain.impl

import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.utill.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {
    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>> {
        return repository.searchMovies(expression).map { results ->
            when (results) {
                is Resource.Success -> {
                    Pair(results.data, null)
                }

                is Resource.Error -> {
                    Pair(null, results.message)
                }
            }

        }
    }

    override fun getMovieDetail(id: String): Flow<Pair<MovieDetail?, String?>> {
        return repository.getDetails(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getMovieFullCast(id: String): Flow<Pair<MovieCast?, String?>> {
        return repository.getMovieCast(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }

        }
    }

    override fun searchNames(namesQuery: String): Flow<Pair<List<Names>?, String?>> {
        return repository.getNames(namesQuery).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun addMoviesToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}
