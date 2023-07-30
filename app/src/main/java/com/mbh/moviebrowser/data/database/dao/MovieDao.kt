package com.mbh.moviebrowser.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mbh.moviebrowser.data.database.model.RoomMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: Collection<RoomMovie>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<RoomMovie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Long): RoomMovie
}