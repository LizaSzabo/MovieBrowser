package com.mbh.moviebrowser.features.movieDetails

import com.mbh.moviebrowser.domain.model.Movie

sealed class MovieDetailsUIState {
    object Loading : MovieDetailsUIState()
    class MovieListReady(val movie: Movie) : MovieDetailsUIState()
    class Error(val errorMessage: String) : MovieDetailsUIState()
}