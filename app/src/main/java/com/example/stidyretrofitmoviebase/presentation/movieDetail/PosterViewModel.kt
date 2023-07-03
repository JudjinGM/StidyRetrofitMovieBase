package com.example.stidyretrofitmoviebase.presentation.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stidyretrofitmoviebase.presentation.model.PosterState

class PosterViewModel(private val posterUrl: String) : ViewModel() {

    private val stateLiveData = MutableLiveData<PosterState>()

    init {
        stateLiveData.postValue(PosterState.Success(posterUrl))
    }

    fun observeState(): LiveData<PosterState> = stateLiveData

    companion object {
        fun getViewModelFactory(posterUrl: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PosterViewModel(posterUrl = posterUrl)
            }
        }
    }
}