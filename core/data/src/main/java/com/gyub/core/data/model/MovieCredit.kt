package com.gyub.core.data.model

import com.gyub.core.common.extensions.orDefault
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.network.model.MovieCreditsResponse

/**
 * 영화 크레딧 모델 Mapper
 *
 * @author   Gyub
 * @created  2024/08/18
 */

fun MovieCreditsResponse.toDomainModel(): MovieCreditsModel = MovieCreditsModel(
    crew = crew.map { it.toDomainModel() },
    cast = cast.map { it.toDomainModel() }
)

fun MovieCreditsResponse.CastMemberResponse.toDomainModel(): MovieCreditsModel.CastMemberModel =
    MovieCreditsModel.CastMemberModel(
        id = id,
        name = name,
        character = character,
        profilePath = profilePath.orEmpty()
    )

fun MovieCreditsResponse.CrewMemberResponse?.toDomainModel(): MovieCreditsModel.CrewMemberModel =
    MovieCreditsModel.CrewMemberModel(
        id = this?.id.orDefault(),
        job = this?.job.orEmpty(),
        name = this?.name.orEmpty(),
        profilePath = this?.profilePath.orEmpty()
    )
