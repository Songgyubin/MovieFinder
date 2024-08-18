package com.gyub.core.domain.model

/**
 * 영화 상세 도메인 모델
 *
 * @author   Gyub
 * @created  2024/08/18
 */
data class MovieDetailModel(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val genres: List<GenreModel> = emptyList(),
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val runtime: Int = 0,
    val releaseDate: String = "",
    val posterUrl: String = "",
    val isBookmarked: Boolean = false,
) {
    data class GenreModel(
        val id: Int = 0,
        val name: String = "",
    )
}