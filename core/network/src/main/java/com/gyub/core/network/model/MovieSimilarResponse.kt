package com.gyub.core.network.model

import kotlinx.serialization.Serializable

/**
 * 유사 영화 응답 모델
 *
 * @author   Gyub
 * @created  2024/08/30
 */
@Serializable
data class MovieSimilarResponse(
    val results: List<MovieResponse>,
)