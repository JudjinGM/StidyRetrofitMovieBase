package com.example.stidyretrofitmoviebase.di

import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<MoviesInteractor> {
        MoviesInteractorImpl(repository = get())
    }
}