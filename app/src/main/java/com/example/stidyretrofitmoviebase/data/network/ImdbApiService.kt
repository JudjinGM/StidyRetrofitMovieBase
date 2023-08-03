package com.example.stidyretrofitmoviebase.data.network

import com.example.stidyretrofitmoviebase.BuildConfig
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieCastResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieDetailsResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesSearchResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.NamesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApiService {
    @GET("/en/API/SearchMovie/${BuildConfig.API_KEY}/{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/${BuildConfig.API_KEY}/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailsResponse

    @GET("/en/API/FullCast/${BuildConfig.API_KEY}/{movie_id}")
    suspend fun getFullCast(@Path("movie_id") movieId: String): MovieCastResponse

    @GET("/en/API/SearchName/${BuildConfig.API_KEY}/{expression}")
    suspend fun getNames(@Path("expression") nameQuery: String): NamesSearchResponse
}