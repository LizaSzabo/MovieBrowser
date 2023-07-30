package com.mbh.moviebrowser.domain.model

import com.mbh.moviebrowser.data.database.model.RoomMovie
import com.mbh.moviebrowser.data.network.model.GetMovieResponse

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
    coverUrl = "https://image.tmdb.org/t/p/w500$poster_path",
    rating = vote_average,
    isFavorite = false,
)

fun Movie.toRoomMovie() = RoomMovie(
    id = id,
    title = title,
    genres = genres,
    overview = overview,
    coverUrl = coverUrl,
    rating = rating,
    isFavorite = false,
)

fun RoomMovie.toMovie() = Movie(
    id = id,
    title = title,
    genres = genres,
    overview = overview,
    coverUrl = coverUrl,
    rating = rating,
    isFavorite = false,
)


