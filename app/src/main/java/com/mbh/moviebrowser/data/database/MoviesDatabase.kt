package com.mbh.moviebrowser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mbh.moviebrowser.data.database.dao.GenreDao
import com.mbh.moviebrowser.data.database.dao.MovieDao
import com.mbh.moviebrowser.data.database.model.RoomGenre
import com.mbh.moviebrowser.data.database.model.RoomMovie

@Database(
    exportSchema = false,
    version = 1,
    entities = [RoomGenre::class, RoomMovie::class]
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao
}