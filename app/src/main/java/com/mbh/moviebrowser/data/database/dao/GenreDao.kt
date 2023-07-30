package com.mbh.moviebrowser.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mbh.moviebrowser.data.database.model.RoomGenre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGenres(genres: Collection<RoomGenre>)

    @Query("SELECT * FROM genre WHERE id = :genreId")
    fun getGenre(genreId: Long): RoomGenre?
}