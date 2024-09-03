package com.gyub.core.data.model

import com.gyub.core.db.model.MovieEntity
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.network.model.base.BaseMovieResponse

fun BaseMovieResponse.toDomainModel() = MovieModel(
    id = id ?: 0,
    title = title.orEmpty(),
    posterUrl = posterPath.orEmpty(),
    voteAverage = voteAverage ?: 0.0,
    overview = overview.orEmpty(),
    isBookmarked = false
)

fun MovieEntity.toDomainModel() = MovieModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    voteAverage = voteAverage,
    overview = overview,
    isBookmarked = isBookmarked
)

fun MovieModel.toEntity() = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    voteAverage = voteAverage,
    overview = overview,
    isBookmarked = isBookmarked
)