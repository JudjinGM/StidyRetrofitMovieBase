package com.example.stidyretrofitmoviebase.presentation.names.models

import com.example.stidyretrofitmoviebase.domain.models.Names

sealed interface NamesState{
    object Loading : NamesState

    data class Content(
        val names: List<Names>
    ) : NamesState

    data class Error(
        val errorMessage: String
    ) : NamesState

    data class Empty(
        val message: String
    ) : NamesState
}