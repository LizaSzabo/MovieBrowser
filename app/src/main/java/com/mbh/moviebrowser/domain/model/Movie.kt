package com.mbh.moviebrowser.domain.model

import com.mbh.moviebrowser.data.network.model.GetMovieResponse
import com.mbh.moviebrowser.data.network.model.GetMoviesResponse

data class Movie(
    val id: Long,
    val title: String,
    val genres: String,
    val overview: String?,
    val coverUrl: String?,
    val rating: Float,
    val isFavorite: Boolean,
)

fun GetMovieResponse.toMovie(genresString: String) = Movie(
    id = id,
    title = title,
    genres = genresString,
    overview = overview,
    coverUrl = poster_path,
    rating = vote_average,
    isFavorite = false,
)
