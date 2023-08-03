package com.example.stidyretrofitmoviebase.presentation.movieCast.models

import com.example.stidyretrofitmoviebase.ui.movieCast.RVItem

interface MovieCastState {
    object Loading : MovieCastState
    class Content(
        val fullTitle: String,
        val items: List<RVItem>
    ) : MovieCastState

    class Error(val message: String) : MovieCastState
}