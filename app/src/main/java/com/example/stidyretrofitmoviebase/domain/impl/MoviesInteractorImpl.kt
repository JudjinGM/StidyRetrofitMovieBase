package com.example.stidyretrofitmoviebase.domain.impl

import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.utill.HandleResult
import java.util.concurrent.Executors.newCachedThreadPool

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = newCachedThreadPool()
    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            repository.searchMovies(expression).handle(object : HandleResult<List<Movie>> {
                override fun handleSuccess(data: List<Movie>?) {
                    consumer.consume(data, null)
                }

                override fun handleError(message: String?, data: List<Movie>?) {
                    consumer.consume(null, message)
                }

            })
        }
    }

    override fun addMoviesToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}
