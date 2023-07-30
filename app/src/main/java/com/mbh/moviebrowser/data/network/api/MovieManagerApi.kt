package com.mbh.moviebrowser.data.network.api

import com.mbh.moviebrowser.data.network.model.GetGenreResponse
import com.mbh.moviebrowser.data.network.model.GetGenresResponse
import com.mbh.moviebrowser.data.network.model.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieManagerApi {

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiI4NTk4YTNkODkwY2M0NGMzNTQzYzcxNDcwYTA0MmJkOCIsInN1YiI6IjYyMjdkODZlZTkyZDgzMDA2OGM5ZTRjYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LbMklMw43lluozwmmKO2JKWL6pSCShYLtXg1PprrbJM"
    )
    @GET("genre/movie/list")
    suspend fun getGenres(): GetGenresResponse

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9" +
                "" +
                ".eyJhdWQiOiI4NTk4YTNkODkwY2M0NGMzNTQzYzcxNDcwYTA0MmJkOCIsInN1YiI6IjYyMjdkODZlZTkyZDgzMDA2OGM5ZTRjYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LbMklMw43lluozwmmKO2JKWL6pSCShYLtXg1PprrbJM",
    )
    @GET("trending/movie/week")
    suspend fun getMovies(@Query("page") page: Int): GetMoviesResponse

}