package com.mbh.moviebrowser.features.movieDetails

import androidx.lifecycle.ViewModel
import com.mbh.moviebrowser.store.MovieStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor() : ViewModel() {

    val movie = MovieStore.movies.map { it.firstOrNull { it.id == MovieStore.detailsId.value } }

    fun onFavoriteClicked(isFavorite: Boolean) {
    }
}
