package com.example.stidyretrofitmoviebase.ui.poster

import android.app.Activity
import android.os.Bundle
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.domain.Creator

class PosterActivity : Activity() {

    private val posterController = Creator.providePosterController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}