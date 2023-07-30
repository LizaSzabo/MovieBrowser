package com.mbh.moviebrowser.domain.interactors

import android.util.Log
import com.mbh.moviebrowser.data.database.source.GenreDataSource
import com.mbh.moviebrowser.data.database.source.MovieDataSource
import com.mbh.moviebrowser.data.network.model.toRoomGenre
import com.mbh.moviebrowser.data.network.source.MovieNetworkDataSource
import com.mbh.moviebrowser.data.network.util.NetworkError
import com.mbh.moviebrowser.data.network.util.NetworkResult
import com.mbh.moviebrowser.data.network.util.NetworkUnavailable
import com.mbh.moviebrowser.data.network.util.UnknownHostError
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.domain.model.toMovie
import com.mbh.moviebrowser.domain.model.toRoomMovie
import com.mbh.moviebrowser.features.util.PresentationLocalResult
import com.mbh.moviebrowser.features.util.PresentationNetworkError
import com.mbh.moviebrowser.features.util.PresentationResponse
import com.mbh.moviebrowser.features.util.PresentationResult
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieDataSource: MovieDataSource,
    private val genreDataSource: GenreDataSource,
) {

    companion object {

        private val favouriteMovies = mutableListOf<Long>()
        private val loadedMovies = mutableListOf<Movie>()
        private var lastPage = 0
    }

    suspend fun getGenres(): PresentationResponse<String> {
        return when (val getGenresResponse = movieNetworkDataSource.getGenres()) {
            is NetworkError -> {
                PresentationNetworkError(getGenresResponse.errorMessage)
            }
            UnknownHostError -> PresentationNetworkError("NoNetworkError")
            NetworkUnavailable -> {
                val roomGenres = genreDataSource.getGenres()
                if (roomGenres.isEmpty()) {
                    PresentationNetworkError("No Internet")
                } else {
                    PresentationResult("")
                }
            }
            is NetworkResult -> {
                val roomGenres = getGenresResponse.result.genres.map { genreFromApi -> genreFromApi.toRoomGenre() }
                genreDataSource.saveGenres(roomGenres)
                PresentationResult("")
            }
        }
    }

    suspend fun getMovies(currentPage: Int): PresentationResponse<List<Movie>> {
        if (lastPage == currentPage) {
            val moviesWithFavouriteMark = loadedMovies.map { movie ->
                if (favouriteMovies.contains(movie.id)) movie.copy(isFavorite = true)
                else movie
            }
            return PresentationResult(moviesWithFavouriteMark)
        } else {
            lastPage = currentPage
            return when (val getMoviesResponse = movieNetworkDataSource.getMovies(currentPage)) {
                is NetworkError -> {
                    PresentationNetworkError(getMoviesResponse.errorMessage)
                }
                UnknownHostError -> PresentationNetworkError("NoNetworkError")
                NetworkUnavailable -> {
                    val currentMoviesFromDB = movieDataSource.getMovies().map { roomMovie -> roomMovie.toMovie() }
                    if (currentMoviesFromDB.isEmpty()) {
                        PresentationNetworkError("No Internet")
                    } else {
                        val moviesWithFavouriteMark = currentMoviesFromDB.map { movie ->
                            if (favouriteMovies.contains(movie.id)) movie.copy(isFavorite = true)
                            else movie
                        }
                        PresentationLocalResult(moviesWithFavouriteMark)
                    }
                }
                is NetworkResult -> {
                    Log.i("networkResult", "result")
                    val movies =
                        getMoviesResponse.result.results.map { movieFromApi -> movieFromApi.toMovie(getGenresString(movieFromApi.genre_ids)) }
                    val roomMovies = movies.map { movie -> movie.toRoomMovie() }
                    movieDataSource.saveMovies(roomMovies)
                    loadedMovies.addAll(movies)
                    val moviesWithFavouriteMark = loadedMovies.map { movie ->
                        if (favouriteMovies.contains(movie.id)) movie.copy(isFavorite = true)
                        else movie
                    }
                    PresentationResult(moviesWithFavouriteMark)
                }
            }
        }
    }

    private fun getGenresString(genresIds: List<Long>): String {
        var genresListAsString = ""
        val genresNameList = genresIds.map { genreId -> getGenreById(genreId) }
        genresNameList.forEach { genreName -> genresListAsString = "$genresListAsString$genreName, " }
        genresListAsString = genresListAsString.dropLast(2)
        return genresListAsString
    }

    private fun getGenreById(genreId: Long): String {
        return genreDataSource.getGenreById(genreId)?.name ?: ""
    }

    fun getDetails(movieId: Long): Movie {
        val movieFromDB = movieDataSource.getMovieById(movieId).toMovie()
        return if (favouriteMovies.contains(movieFromDB.id)) movieFromDB.copy(isFavorite = true) else movieFromDB
    }

    fun addToFavouritesList(movieId: Long) {
        favouriteMovies.add(movieId)
    }

    fun deleteFromFavouritesList(movieId: Long) {
        favouriteMovies.remove(movieId)
    }

}