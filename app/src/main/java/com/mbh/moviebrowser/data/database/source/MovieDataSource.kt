package com.mbh.moviebrowser.data.database.source

import com.mbh.moviebrowser.data.database.dao.MovieDao
import com.mbh.moviebrowser.data.database.model.RoomMovie

class MovieDataSource(
    private val movieDao: MovieDao
) {

    fun saveMovies(roomMovies: List<RoomMovie>) {
        movieDao.saveMovies(roomMovies)
    }

    fun getMovies(): List<RoomMovie> {
        return movieDao.getAllMovies()
    }
}