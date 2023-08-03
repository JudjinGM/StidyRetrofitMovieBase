package com.example.stidyretrofitmoviebase.data.dto.imdb

import com.example.stidyretrofitmoviebase.data.dto.Response

class NamesSearchResponse(
    val searchType: String, val expression: String, val results: List<NamesDto>
) : Response()