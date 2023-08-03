package com.example.stidyretrofitmoviebase.data.dto.imdb

import com.example.stidyretrofitmoviebase.data.dto.Response

class MovieCastResponse(
    val actors: List<ActorResponse>,
    val directors: DirectorsResponse,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val others: List<OtherResponse>,
    val title: String,
    val type: String,
    val writers: WritersResponse,
    val year: String
) : Response()