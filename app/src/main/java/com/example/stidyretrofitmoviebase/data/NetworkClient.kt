package com.example.stidyretrofitmoviebase.data

import com.example.stidyretrofitmoviebase.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}