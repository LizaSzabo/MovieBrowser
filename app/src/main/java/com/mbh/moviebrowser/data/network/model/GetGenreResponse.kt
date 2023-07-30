package com.mbh.moviebrowser.data.network.model

import com.mbh.moviebrowser.data.database.model.RoomGenre

data class GetGenreResponse(
    val id: Long,
    val name: String,
)

fun GetGenreResponse.toRoomGenre() = RoomGenre(
    id = id,
    name = name,
)