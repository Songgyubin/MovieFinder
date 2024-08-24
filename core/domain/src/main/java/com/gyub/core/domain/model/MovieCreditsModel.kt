package com.gyub.core.domain.model

/**
 * 영화 크레딧 모델
 *
 * @author   Gyub
 * @created  2024/08/18
 */
data class MovieCreditsModel(
    val crew: List<CrewMemberModel> = emptyList(),
    val cast: List<CastMemberModel> = emptyList(),
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

    fun getDirector(): CrewMemberModel {
        return crew.firstOrNull { it.job == "Director" } ?: CrewMemberModel()
    }
}