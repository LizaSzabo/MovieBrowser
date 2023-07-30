package com.mbh.moviebrowser.data.database.source

import com.mbh.moviebrowser.data.database.dao.GenreDao
import com.mbh.moviebrowser.data.database.model.RoomGenre

class GenreDataSource(
    private val genreDao: GenreDao
) {

    fun saveGenres(roomGenres: List<RoomGenre>) {
        genreDao.saveGenres(roomGenres)
    }

    fun getGenreById(id: Long): RoomGenre? {
        return genreDao.getGenre(id)
    }

    fun getGenres(): List<RoomGenre>{
        return genreDao.getGenres()
    }
}