package com.example.stidyretrofitmoviebase.presentation.model

sealed interface PosterState {
    class Success(val url: String):PosterState
}