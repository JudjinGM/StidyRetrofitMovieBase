package com.example.stidyretrofitmoviebase.presentation.names

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.presentation.names.models.NamesState
import com.example.stidyretrofitmoviebase.utill.debounce
import kotlinx.coroutines.launch

class NamesSearchViewModel(
    private val moviesInteractor: MoviesInteractor, private val context: Context
) : ViewModel() {

    private val stateLiveData = MutableLiveData<NamesState>()

    fun observeState(): LiveData<NamesState> = stateLiveData

    private var latestSearchText: String? = null

    private val namesSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {
            searchRequest(it)
        }

    fun searchDebounce(changedText: String) {
        if (this.latestSearchText == changedText) {
            return
        }
        this.latestSearchText = changedText

        namesSearchDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            postState(NamesState.Loading)

            viewModelScope.launch {
                moviesInteractor.searchNames(newSearchText).collect { pair ->
                    processResult(pair.first, pair.second)
                }
            }
        }
    }

    private fun processResult(foundNames: List<Names>?, errorMessage: String?) {
        val persons = mutableListOf<Names>()
        if (foundNames != null) {
            persons.addAll(foundNames)
        }
        when {
            errorMessage != null -> {
                postState(NamesState.Error(errorMessage))
            }

            persons.isEmpty() -> {
                postState(NamesState.Empty(R.string.nothing_found.toString()))
            }

            else -> postState(NamesState.Content(persons))
        }
    }

    private fun postState(state: NamesState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}