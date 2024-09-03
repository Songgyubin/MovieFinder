package com.gyub.core.network.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 영화 공통 응답 모델
 *
 * @author   Gyub
 * @created  2024/08/30
 */
@Serializable
open class BaseMovieResponse(
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("genre_ids")
    val genreIds: List<Int>? = emptyList(),
    val id: Int? = 0,
    @SerialName("media_type")
    val mediaType: String? = "",
    @SerialName("original_language")
    val originalLanguage: String? = "",
    @SerialName("original_title")
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("release_date")
    val releaseDate: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0,
)