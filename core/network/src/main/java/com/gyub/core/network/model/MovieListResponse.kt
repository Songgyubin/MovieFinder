package com.gyub.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 영화 리스트 응답 모델
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@Serializable
data class MovieListResponse(
    val dates: Dates? = null,
    val page: Int? = null,
    val results: List<Movie>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Long? = null,
) {
    @Serializable
    data class Movie(
        val adult: Boolean?,
        @SerialName("backdrop_path")
        val backdropPath: String?,
        @SerialName("genre_ids")
        val genreIds: List<Int>?,
        val id: Int?,
        @SerialName("original_language")
        val originalLanguage: String?,
        @SerialName("original_title")
        val originalTitle: String?,
        val overview: String?,
        val popularity: Double?,
        @SerialName("poster_path")
        val posterUrl: String?,
        @SerialName("release_date")
        val releaseDate: String?,
        val title: String?,
        val video: Boolean?,
        @SerialName("vote_average")
        val voteAverage: Double?,
        @SerialName("vote_count")
        val voteCount: Int?,
    )

    @Serializable
    data class Dates(
        val maximum: String?,
        val minimum: String?,
    )
}