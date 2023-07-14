package com.example.stidyretrofitmoviebase.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.stidyretrofitmoviebase.data.NetworkClient
import com.example.stidyretrofitmoviebase.data.dto.Response
import com.example.stidyretrofitmoviebase.data.dto.imdb.MovieDetailsRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesCastRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.MoviesSearchRequest
import com.example.stidyretrofitmoviebase.data.dto.imdb.NamesSearchRequest

class RetrofitNetworkClientImdb(
    private val context: Context,
    private val imdbService: ImdbApiService
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                val resp = imdbService.searchMovies(dto.expression).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is MovieDetailsRequest -> {
                val resp = imdbService.getMovieDetails(dto.movieId).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is MoviesCastRequest -> {
                val resp = imdbService.getFullCast(dto.id).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is NamesSearchRequest -> {
                val resp = imdbService.getNames(dto.names).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            else -> Response().apply { resultCode = 400 }

        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}