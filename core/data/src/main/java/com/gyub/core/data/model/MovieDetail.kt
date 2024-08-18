package com.gyub.core.data.model

import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.network.model.MovieDetailResponse

/**
 * 영화 상세 Mapper
 *
 * @author   Gyub
 * @created  2024/08/18
 */

fun MovieDetailResponse.toDomainModel(): MovieDetailModel = MovieDetailModel(
    id = id,
    title = title,
    overview = overview,
    genres = genres.map { genre -> MovieDetailModel.GenreModel(id = genre.id, name = genre.name) },
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime,
    releaseDate = releaseDate,
    posterUrl = belongsToCollection?.posterPath.orEmpty(),
    isBookmarked = false
)