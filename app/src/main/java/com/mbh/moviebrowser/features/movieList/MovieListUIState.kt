package com.mbh.moviebrowser.features.movieList

import com.mbh.moviebrowser.domain.model.Movie

sealed class MovieListUIState {
    object Loading : MovieListUIState()
    class MovieListReady(val movies: List<Movie>) : MovieListUIState()

    class LocalMovieListReady(val movies: List<Movie>) : MovieListUIState()
    class Error(val errorMessage: String) : MovieListUIState()
}