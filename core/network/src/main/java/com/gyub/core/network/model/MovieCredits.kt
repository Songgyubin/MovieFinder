package com.gyub.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 영화 크레딧 응답 모델
 *
 * @author   Gyub
 * @created  2024/08/18
 */
@Serializable
data class MovieCreditsResponse(
    val id: Int,
    val cast: List<CastMemberResponse>,
    val crew: List<CrewMemberResponse>,
) {
    @Serializable
    data class CastMemberResponse(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        @SerialName("known_for_department")
        val knownForDepartment: String,
        val name: String,
        @SerialName("original_name")
        val originalName: String,
        val popularity: Double,
        @SerialName("profile_path")
        val profilePath: String?,
        @SerialName("cast_id")
        val castId: Int,
        val character: String,
        @SerialName("credit_id")
        val creditId: String,
        val order: Int,
    )

    @Serializable
    data class CrewMemberResponse(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        @SerialName("known_for_department")
        val knownForDepartment: String,
        val name: String,
        @SerialName("original_name")
        val originalName: String,
        val popularity: Double,
        @SerialName("profile_path")
        val profilePath: String?,
        @SerialName("credit_id")
        val creditId: String,
        val department: String,
        val job: String,
    )
}
