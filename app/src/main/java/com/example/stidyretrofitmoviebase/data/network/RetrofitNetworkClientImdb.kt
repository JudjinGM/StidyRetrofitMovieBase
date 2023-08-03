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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClientImdb(
    private val context: Context,
    private val imdbService: ImdbApiService
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.searchMovies(dto.expression)
                        response.apply {
                            resultCode = 200
                        }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is MovieDetailsRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getMovieDetails(dto.movieId)
                        response.apply {
                            resultCode = 200
                        }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is MoviesCastRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getFullCast(dto.id)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is NamesSearchRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getNames(dto.names)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
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