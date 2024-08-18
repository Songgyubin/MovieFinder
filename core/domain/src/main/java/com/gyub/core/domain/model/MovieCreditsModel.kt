package com.gyub.core.domain.model

/**
 * 영화 크레딧 모델
 *
 * @author   Gyub
 * @created  2024/08/18
 */
data class MovieCreditsModel(
    val director: CrewMemberModel? = null,
    val cast: List<CastMemberModel> = emptyList(),
) {
    data class CastMemberModel(
        val id: Int,
        val name: String,
        val character: String,
        val profilePath: String,
    )

    data class CrewMemberModel(
        val id: Int,
        val job: String,
        val name: String,
        val profilePath: String,
    )
}