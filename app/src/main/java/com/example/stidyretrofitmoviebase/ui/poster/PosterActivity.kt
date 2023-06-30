package com.example.stidyretrofitmoviebase.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.presentation.poster.PosterPresenter
import com.example.stidyretrofitmoviebase.presentation.poster.PosterView
import com.example.stidyretrofitmoviebase.utill.Creator

class PosterActivity : Activity(), PosterView {

    private lateinit var posterPresenter: PosterPresenter
    private lateinit var poster: ImageView
    private lateinit var imageUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        imageUrl = intent.extras?.getString("poster", "") ?: ""

        posterPresenter = Creator.providePosterController(this, imageUrl)
        poster = findViewById(R.id.poster)
        posterPresenter.onCreate()
    }

    override fun setupPosterImage(url: String) {
        Glide.with(applicationContext).load(url).into(poster)
    }
}