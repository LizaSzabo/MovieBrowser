package com.mbh.moviebrowser.features.movieDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbh.moviebrowser.domain.interactors.MovieInteractor
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

    val movie = MovieStore.movies.map { it.firstOrNull { it.id == MovieStore.detailsId.value } }

    fun onFavoriteClicked(isFavorite: Boolean) {
    }

    fun getDetails(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = movieInteractor.getDetails(movieId)
            Log.i("movie details", movie.toString())
            _uiState.emit(MovieDetailsUIState.MovieReady(movie))
        }
    }
}
