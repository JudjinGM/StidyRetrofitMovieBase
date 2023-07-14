package com.example.stidyretrofitmoviebase.presentation.movieDetail.models

sealed interface PosterState {
    class Success(val url: String): PosterState
}