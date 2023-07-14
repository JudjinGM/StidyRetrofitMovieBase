package com.example.stidyretrofitmoviebase.presentation.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stidyretrofitmoviebase.presentation.movieDetail.models.PosterState

class PosterViewModel(posterUrl: String) : ViewModel() {

    private val stateLiveData = MutableLiveData<PosterState>()

    init {
        stateLiveData.postValue(PosterState.Success(posterUrl))
    }

    fun observeState(): LiveData<PosterState> = stateLiveData

}
