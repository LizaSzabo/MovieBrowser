package com.mbh.moviebrowser.features.movieList

import androidx.lifecycle.ViewModel
import com.mbh.moviebrowser.domain.Movie
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Loading
import com.mbh.moviebrowser.store.MovieStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUIState>(Loading)
    val uiState: StateFlow<MovieListUIState> = _uiState.asStateFlow()

    val movies = MovieStore.movies

    fun storeMovieForNavigation(movie: Movie) {
        MovieStore.detailsId.value = movie.id
    }
}
