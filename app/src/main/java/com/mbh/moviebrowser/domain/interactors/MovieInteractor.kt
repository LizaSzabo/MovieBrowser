package com.mbh.moviebrowser.domain.interactors

import com.mbh.moviebrowser.data.network.source.MovieNetworkDataSource
import com.mbh.moviebrowser.data.network.util.NetworkError
import com.mbh.moviebrowser.data.network.util.NetworkResult
import com.mbh.moviebrowser.data.network.util.NetworkUnavailable
import com.mbh.moviebrowser.data.network.util.UnknownHostError
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationResponse
import com.mbh.moviebrowser.features.util.PresentationResult
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource
) {

    suspend fun getMovies(): PresentationResponse<List<Movie>> {
        return when (val getCocktailsResponse = movieNetworkDataSource.getMovies()) {
            is NetworkError -> {
                PresentationNetworkError(getCocktailsResponse.errorMessage)
            }
            UnknownHostError -> PresentationNetworkError("NoNetworkError")
            NetworkUnavailable -> {
                PresentationLocalResult(
                    listOf(
                        Movie(
                            id = 111, title = "", genres = "", rating = 1.0F, isFavorite = false, overview =
                            null, coverUrl = null
                        )
                    )
                )
               // PresentationNetworkError("No Internet")
            }
            is NetworkResult -> {
                PresentationResult( listOf(
                    Movie(
                        id = 111, title = "AAAAAA", genres = "", rating = 6.0F, isFavorite = false, overview =
                        null, coverUrl = null
                    )
                ))
            }
        }
    }
}