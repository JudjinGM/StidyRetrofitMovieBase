package com.example.stidyretrofitmoviebase.presentation.movieDetail

class PosterPresenter(private val view: PosterView, private val imageUrl: String) {
    fun onCreate() {
        view.setupPosterImage(imageUrl)
    }
}
