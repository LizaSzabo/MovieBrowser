package com.mbh.moviebrowser.data.network.api

import com.mbh.moviebrowser.data.network.model.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MovieManagerApi {

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiI4NTk4YTNkODkwY2M0NGMzNTQzYzcxNDcwYTA0MmJkOCIsInN1YiI6IjYyMjdkODZlZTkyZDgzMDA2OGM5ZTRjYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LbMklMw43lluozwmmKO2JKWL6pSCShYLtXg1PprrbJM"
    )
    @GET("trending/movie/week")
    suspend fun getMovies(): GetMoviesResponse

}