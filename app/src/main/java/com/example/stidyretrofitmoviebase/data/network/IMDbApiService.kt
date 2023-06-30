package com.example.stidyretrofitmoviebase.data.network

import com.example.stidyretrofitmoviebase.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_moqmnxji/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}