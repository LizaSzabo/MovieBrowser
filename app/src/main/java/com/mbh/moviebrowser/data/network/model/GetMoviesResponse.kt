package com.mbh.moviebrowser.data.network.model

data class GetMoviesResponse(
    val page: Int,
    val results: List<GetMovieResponse>,
    val total_pages: Int,
    val total_results: Int,
)