package com.example.stidyretrofitmoviebase.presentation.poster

class PosterPresenter(private val view: PosterView, private val imageUrl: String) {


    fun onCreate() {
        view.setupPosterImage(imageUrl)
    }
}
