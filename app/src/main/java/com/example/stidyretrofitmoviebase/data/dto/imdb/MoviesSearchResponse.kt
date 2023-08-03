package com.example.stidyretrofitmoviebase.data.dto.imdb

import com.example.stidyretrofitmoviebase.data.dto.Response

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MoviesDto>): Response()

