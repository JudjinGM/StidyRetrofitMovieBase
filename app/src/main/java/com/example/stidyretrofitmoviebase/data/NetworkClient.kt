package com.example.stidyretrofitmoviebase.data

import com.example.stidyretrofitmoviebase.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}