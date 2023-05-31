package com.example.stidyretrofitmoviebase.domain.impl

import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import java.util.concurrent.Executors.newCachedThreadPool

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {
    private val executor = newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            consumer.consume(repository.searchMovies(expression))
        }
    }
}