package com.mbh.moviebrowser.features.movieDetails

import com.mbh.moviebrowser.domain.model.Movie

sealed class MovieDetailsUIState {
    object Loading : MovieDetailsUIState()
    class MovieReady(val movie: Movie) : MovieDetailsUIState()
}