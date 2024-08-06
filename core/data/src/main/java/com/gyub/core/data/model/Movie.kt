package com.gyub.core.data.model

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.network.model.MovieListResponse

fun MovieListResponse.Movie.toDomainModel() = MovieModel(
    id = id ?: 0,
    title = title.orEmpty(),
    posterUrl = posterUrl.orEmpty(),
    voteAverage = voteAverage ?: 0.0,
    overview = overview.orEmpty(),
)