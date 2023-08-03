package com.example.stidyretrofitmoviebase.ui.movieCast

import com.example.stidyretrofitmoviebase.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem: RVItem {
    data class HeaderItem(val headerText: String) : MoviesCastRVItem
    data class PersonItem(val data: MovieCastPerson) : MoviesCastRVItem
}