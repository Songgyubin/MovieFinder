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
    val results: List<MovieResponse>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Long? = null,
) {
    @Serializable
    data class Dates(
        val maximum: String?,
        val minimum: String?,
    )
}