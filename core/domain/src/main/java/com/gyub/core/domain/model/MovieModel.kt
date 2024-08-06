package com.gyub.core.domain.model

/**
 * 영화 도메인 모델
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
    val isBookmarked: Boolean = false,
)