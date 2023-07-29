package com.mbh.moviebrowser.data.network.model

data class GetMovieResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Long,
    val title: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Double,
)