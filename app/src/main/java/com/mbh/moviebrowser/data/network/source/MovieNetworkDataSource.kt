package com.mbh.moviebrowser.data.network.source

import com.mbh.moviebrowser.data.network.api.MovieManagerApi
import com.mbh.moviebrowser.data.network.util.apiCall
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(
    private val movieManagerApi: MovieManagerApi
) {

    suspend fun getGenres() = apiCall {
        movieManagerApi.getGenres()
    }

    suspend fun getMovies(page: Int) = apiCall {
        movieManagerApi.getMovies(page = page)
    }
}