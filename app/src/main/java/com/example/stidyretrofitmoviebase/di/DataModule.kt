package com.example.stidyretrofitmoviebase.di

import LocalStorage
import android.content.Context
import android.content.SharedPreferences
import com.example.stidyretrofitmoviebase.data.MovieCastConverter
import com.example.stidyretrofitmoviebase.data.MoviesRepositoryImdbImpl
import com.example.stidyretrofitmoviebase.data.NetworkClient
import com.example.stidyretrofitmoviebase.data.network.ImdbApiService
import com.example.stidyretrofitmoviebase.data.network.RetrofitNetworkClientImdb
import com.example.stidyretrofitmoviebase.domain.api.MoviesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "local_storage",
            Context.MODE_PRIVATE,
        )
    }

    single<LocalStorage> {
        LocalStorage(sharedPreferences = get())
    }

    single<ImdbApiService> {
        Retrofit.Builder().baseUrl("https://imdb-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImdbApiService::class.java)
    }

    single<MovieCastConverter> {
        MovieCastConverter()
    }

    single<NetworkClient> {
        RetrofitNetworkClientImdb(context = get(), imdbService = get())
    }

    single<MoviesRepository> {
        MoviesRepositoryImdbImpl(
            networkClient = get(),
            localStorage = get(),
            movieCastConverter = get()
        )
    }
}