package com.mbh.moviebrowser.features.movieList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbh.moviebrowser.domain.interactors.MovieInteractor
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Loading
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Error
import com.mbh.moviebrowser.features.movieList.MovieListUIState.MovieListReady
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationResult
import com.mbh.moviebrowser.store.MovieStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUIState>(Loading)
    val uiState: StateFlow<MovieListUIState> = _uiState.asStateFlow()

    companion object {

        var currentPage = 1
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieInteractor.getGenres()) {
                is PresentationResult -> getMovies(currentPage)
                is PresentationNetworkError -> _uiState.emit(Error(response.message ?: "Network error"))
                is PresentationLocalResult -> getMovies(currentPage)
            }
        }
    }

    private fun getMovies(currentPage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieInteractor.getMovies(currentPage)) {
                is PresentationResult -> _uiState.emit(MovieListReady(response.result))
                is PresentationLocalResult -> _uiState.emit(MovieListUIState.LocalMovieListReady(response.result))
                is PresentationNetworkError -> _uiState.emit(Error(response.message ?: "Network error"))
            }
        }
    }

    fun getMoreMovies() {
        currentPage++
        getMovies(currentPage)
    }
}
