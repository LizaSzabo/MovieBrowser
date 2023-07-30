package com.mbh.moviebrowser.domain.interactors

import android.util.Log
import com.mbh.moviebrowser.data.database.source.GenreDataSource
import com.mbh.moviebrowser.data.database.source.MovieDataSource
import com.mbh.moviebrowser.data.network.source.MovieNetworkDataSource
import com.mbh.moviebrowser.data.network.util.NetworkError
import com.mbh.moviebrowser.data.network.util.NetworkResult
import com.mbh.moviebrowser.data.network.util.NetworkUnavailable
import com.mbh.moviebrowser.data.network.util.UnknownHostError
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.domain.model.toMovie
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationNoResult
import com.mbh.moviebrowser.features.util.PresentationResponse
import com.mbh.moviebrowser.features.util.PresentationResult
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieDataSource: MovieDataSource,
    private val genreDataSource: GenreDataSource,
) {

    suspend fun getGenres(): PresentationResponse<String> {
        return when (val getGenresResponse = movieNetworkDataSource.getGenres()) {
            is NetworkError -> {
                PresentationNetworkError(getGenresResponse.errorMessage)
            }
            UnknownHostError -> PresentationNetworkError("NoNetworkError")
            NetworkUnavailable -> {
                PresentationNetworkError("No Internet")
            }
            is NetworkResult -> {
                Log.i("genres", getGenresResponse.result.toString())
                PresentationResult("")
            }
        }
    }

    suspend fun getMovies(): PresentationResponse<List<Movie>> {
        return when (val getMoviesResponse = movieNetworkDataSource.getMovies()) {
            is NetworkError -> {
                PresentationNetworkError(getMoviesResponse.errorMessage)
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
                val movies =
                    getMoviesResponse.result.results.map { movieFromApi -> movieFromApi.toMovie(getGenresString(movieFromApi.genre_ids)) }
                PresentationResult(movies)
            }
        }
    }

    private fun getGenresString(genresIds: List<Long>): String {
        var genresListAsString = ""
        val genresNameList = genresIds.map { genreId -> getGenreById(genreId) }
        genresNameList.forEach { genreName -> genresListAsString = "$genresListAsString$genreName, " }
        genresListAsString.dropLast(2)
        return genresListAsString
    }

    private fun getGenreById(genreId: Long): String {
        return "aa"
    }
}