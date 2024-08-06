package com.gyub.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 영화 Entity
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Entity(tableName = "bookmarked_movies")
data class MovieEntity(
    @PrimaryKey val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val isBookmarked: Boolean = false
)