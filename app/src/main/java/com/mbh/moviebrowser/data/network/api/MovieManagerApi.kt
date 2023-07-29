package com.mbh.moviebrowser.data.network.api

import com.mbh.moviebrowser.data.network.model.GetMoviesResponse
import retrofit2.http.GET

interface MovieManagerApi {

    @GET("random.php")
    suspend fun getMovies(): GetMoviesResponse

}