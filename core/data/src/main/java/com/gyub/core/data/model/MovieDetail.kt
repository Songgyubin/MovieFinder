package com.gyub.core.data.model

import com.gyub.core.common.extensions.orDefault
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.network.model.MovieDetailResponse

/**
 * 영화 상세 Mapper
 *
 * @author   Gyub
 * @created  2024/08/18
 */

fun MovieDetailResponse.toDomainModel(): MovieDetailModel = MovieDetailModel(
    id = id.orDefault(),
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    genres = genres.map { genre -> MovieDetailModel.GenreModel(id = genre.id, name = genre.name) },
    voteAverage = voteAverage.orDefault(),
    voteCount = voteCount.orDefault(),
    runtime = runtime,
    releaseDate = releaseDate.orEmpty(),
    status = status,
    posterUrl = posterPath.orEmpty(),
    isBookmarked = false
)