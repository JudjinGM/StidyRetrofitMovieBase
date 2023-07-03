package com.example.stidyretrofitmoviebase.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.stidyretrofitmoviebase.data.NetworkClient
import com.example.stidyretrofitmoviebase.data.dto.MovieDetailsRequest
import com.example.stidyretrofitmoviebase.data.dto.MoviesSearchRequest
import com.example.stidyretrofitmoviebase.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(private val context: Context) : NetworkClient {

    private val imdbBaseUrl = "https://imdb-api.com"
    private val retrofit =
        Retrofit.Builder().baseUrl(imdbBaseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java)

    override fun doRequest(dto: Any): Response {
        if (!isConected()) {
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                val resp = imdbService.searchMovies(dto.expression).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }
            is MovieDetailsRequest -> {
                val resp = imdbService.getMovieDetails(dto.id).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            else -> Response().apply { resultCode = 400 }

        }
    }

        private fun isConected(): Boolean {
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