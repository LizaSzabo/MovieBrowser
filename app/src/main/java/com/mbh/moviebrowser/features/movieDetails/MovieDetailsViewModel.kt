package com.mbh.moviebrowser.features.movieDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbh.moviebrowser.domain.interactors.MovieInteractor
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.features.movieList.MovieListUIState
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationResult
import com.mbh.moviebrowser.store.MovieStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUIState>(MovieDetailsUIState.Loading)
    val uiState: StateFlow<MovieDetailsUIState> = _uiState.asStateFlow()

    fun onFavoriteClicked(isFavorite: Boolean, movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(MovieDetailsUIState.Loading)
            val updatedMovie = movie.copy(isFavorite = isFavorite)
            if (isFavorite) {
                movieInteractor.addToFavouritesList(movie.id)
            } else {
                movieInteractor.deleteFromFavouritesList(movie.id)
            }
            _uiState.emit(MovieDetailsUIState.MovieReady(updatedMovie))
        }
    }

    fun getDetails(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(MovieDetailsUIState.Loading)
            val movie = movieInteractor.getDetails(movieId)
            _uiState.emit(MovieDetailsUIState.MovieReady(movie))
        }
    }
}
