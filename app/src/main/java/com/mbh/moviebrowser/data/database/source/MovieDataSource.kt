package com.mbh.moviebrowser.data.database.source

import com.mbh.moviebrowser.data.database.dao.MovieDao
import com.mbh.moviebrowser.data.database.model.RoomMovie

class MovieDataSource(
    private val movieDao: MovieDao
) {

    fun saveMovies(roomMovies: List<RoomMovie>) {
        movieDao.saveMovies(roomMovies)
    }

    fun getMovies(): List<RoomMovie> = movieDao.getAllMovies()

    fun getMovieById(movieId: Long): RoomMovie = movieDao.getMovie(movieId)
}