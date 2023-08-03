package com.example.stidyretrofitmoviebase.data

import LocalStorage
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieCastResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieDetailsRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieDetailsResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesCastRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesSearchRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesSearchResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.NamesSearchRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.NamesSearchResponse
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import com.example.stidyretrofitmoviebase.domain.models.Movie
import com.example.stidyretrofitmoviebase.domain.models.MovieCast
import com.example.stidyretrofitmoviebase.domain.models.MovieDetail
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.utill.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImdbImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {
    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                with(response as MoviesSearchResponse) {
                    val data = results.map {
                        Movie(
                            id = it.id,
                            resultType = it.resultType,
                            image = it.image,
                            title = it.title,
                            description = it.description,
                            inFavorite = stored.contains((it.id))
                        )
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun getDetails(id: String): Flow<Resource<MovieDetail>> = flow {
        val response = networkClient.doRequest(MovieDetailsRequest(id))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> with(response as MovieDetailsResponse) {
                emit(
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
                )
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun getMovieCast(id: String): Flow<Resource<MovieCast>> = flow {
        val response = networkClient.doRequest(MoviesCastRequest(id))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 ->
                emit(
                    Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    )
                )

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun getNames(namesQuery: String): Flow<Resource<List<Names>>> = flow {
        val response = networkClient.doRequest(NamesSearchRequest(namesQuery))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> emit(
                Resource.Success(
                    (response as NamesSearchResponse).results.map { it ->
                        Names(
                            description = it.description,
                            id = it.id,
                            image = it.image,
                            resultType = it.resultType,
                            title = it.title
                        )
                    })
            )

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }


    override fun addMovieToFavorites(movies: Movie) {
        localStorage.addToFavorites(movies.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
}