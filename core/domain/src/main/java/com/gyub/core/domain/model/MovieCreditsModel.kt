package com.gyub.core.domain.model

/**
 * 영화 크레딧 모델
 *
 * @author   Gyub
 * @created  2024/08/18
 */
data class MovieCreditsModel(
    val director: CrewMemberModel = CrewMemberModel(),
    val casts: List<CastMemberModel> = emptyList(),
) {
    data class CastMemberModel(
        val id: Int = 0,
        val name: String = "",
        val character: String = "",
        val profilePath: String = "",
    )

    data class CrewMemberModel(
        val id: Int = 0,
        val job: String = "",
        val name: String = "",
        val profilePath: String = "",
    )
}