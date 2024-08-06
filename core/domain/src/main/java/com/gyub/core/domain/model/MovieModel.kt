package com.gyub.core.domain.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/05
 */
data class MovieModel(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
)