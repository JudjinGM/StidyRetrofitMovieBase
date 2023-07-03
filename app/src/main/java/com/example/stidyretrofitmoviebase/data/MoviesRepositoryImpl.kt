package com.example.stidyretrofitmoviebase.data

import LocalStorage
import MovieDetailsResponse
import com.example.stidyretrofitmoviebase.data.dto.MovieDetailsRequest
import com.example.stidyretrofitmoviebase.data.dto.MoviesSearchRequest
import com.example.stidyretrofitmoviebase.data.dto.MoviesSearchResponse
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.utill.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage
) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                val stored = localStorage.getSavedFavorites()
                Resource.Success((response as MoviesSearchResponse).results.map { it ->
                    Movie(
                        id = it.id,
                        resultType = it.resultType,
                        image = it.image,
                        title = it.title,
                        description = it.description,
                        inFavorite = stored.contains((it.id))
                    )
                })
            }
            else -> Resource.Error("Ошибка сервера")
        }
    }

    override fun getDetails(id: String): Resource<MovieDetail> {
        val response = networkClient.doRequest(MovieDetailsRequest(id))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> with(response as MovieDetailsResponse) {
                Resource.Success(
                    MovieDetail(
                        id,
                        title,
                        imDbRating,
                        year,
                        countries,
                        genres,
                        directors,
                        writers,
                        stars,
                        plot
                    )
                )
            }
            else -> Resource.Error("Ошибка сервера")
        }
    }


    override fun addMovieToFavorites(movies: Movie) {
        localStorage.addToFavorites(movies.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
}