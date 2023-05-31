package com.example.stidyretrofitmoviebase.data.dto

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MoviesDto>):Response()

