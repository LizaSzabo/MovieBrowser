package com.mbh.moviebrowser.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class RoomGenre(
    @PrimaryKey
    val id: Long,
    val name: String,
)