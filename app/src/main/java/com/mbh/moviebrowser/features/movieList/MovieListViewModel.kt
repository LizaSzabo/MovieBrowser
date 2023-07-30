package com.mbh.moviebrowser.features.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbh.moviebrowser.domain.interactors.MovieInteractor
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Loading
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Error
import com.mbh.moviebrowser.features.movieList.MovieListUIState.MovieListReady
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationResult
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

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieInteractor.getGenres()) {
                is PresentationResult -> getMovies(currentPage)
                is PresentationNetworkError -> _uiState.emit(Error(response.message ?: NETWORK_ERROR))
                is PresentationLocalResult -> getMovies(currentPage)
            }
        }
    }

    private fun getMovies(currentPage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieInteractor.getMovies(currentPage)) {
                is PresentationResult -> _uiState.emit(MovieListReady(response.result))
                is PresentationLocalResult -> _uiState.emit(MovieListUIState.LocalMovieListReady(response.result))
                is PresentationNetworkError -> _uiState.emit(Error(response.message ?: NETWORK_ERROR))
            }
        }
    }

    fun getMoreMovies() {
        currentPage++
        getMovies(currentPage)
    }

    companion object {

        private var currentPage = 1
        private const val NETWORK_ERROR = "Network error"
    }
}