package com.example.stidyretrofitmoviebase.presentation.names

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.domain.api.MoviesInteractor
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.presentation.names.models.NamesState

class NamesSearchViewModel(
    private val moviesInteractor: MoviesInteractor, private val context: Context
) : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<NamesState>()


    fun observeState(): LiveData<NamesState> = stateLiveData

    private fun postState(state: NamesState) {
        stateLiveData.postValue(state)
    }

    private var latestSearchText: String? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_NAMES_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        handler.removeCallbacksAndMessages(SEARCH_NAMES_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_NAMES_REQUEST_TOKEN,
            postTime,
        )
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            postState(NamesState.Loading)

            moviesInteractor.searchNames(newSearchText, object : MoviesInteractor.NamesConsummer {
                override fun consume(names: List<Names>?, errorMessage: String?) {
                    val namesList = mutableListOf<Names>()

                    if (names != null) {
                        namesList.addAll(names)
                    }

                    when {
                        errorMessage != null -> {
                            postState(
                                NamesState.Error(
                                    context.getString(R.string.something_went_wrong)
                                )
                            )
                        }

                        namesList.isEmpty() -> {
                            postState(
                                NamesState.Empty(
                                    context.getString(R.string.nothing_found)
                                )
                            )
                        }

                        else -> postState(NamesState.Content(namesList))
                    }
                }
            })
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_NAMES_REQUEST_TOKEN = Any()
    }
}