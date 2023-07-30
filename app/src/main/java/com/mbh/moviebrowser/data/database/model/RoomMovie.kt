package com.mbh.moviebrowser.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class RoomMovie(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val backdrop_path: String,
    val title: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val genre_ids: String,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Double,
)