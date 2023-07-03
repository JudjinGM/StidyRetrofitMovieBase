package com.example.stidyretrofitmoviebase.presentation.model

import com.example.stidyretrofitmoviebase.domain.models.MovieDetail

interface AboutState {
    class Success(val movieDetail: MovieDetail):AboutState
    class Error(val message: String):AboutState
}