package com.gyub.core.domain.usecase

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 추천 영화 가져오는 Use Case
 *
 * @author   Gyub
 * @created  2024/09/03
 */
class GetRecommendationMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int, page: Int = 1): Flow<List<MovieModel>> = flow {
        emit(repository.getRecommendationsMovies(page, movieId))
    }

}