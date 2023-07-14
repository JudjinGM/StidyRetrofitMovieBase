package com.example.stidyretrofitmoviebase.data.network

import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieCastResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieDetailsResponse
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImdbApiServiceMock {
    @GET("/imdb/")
    fun searchMovies(@Query("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/imdb/")
    fun getMovieDetails(@Query("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/imdb/FullCast/")
    fun getFullCast(@Query("movie_id") movieId: String): Call<MovieCastResponse>

}