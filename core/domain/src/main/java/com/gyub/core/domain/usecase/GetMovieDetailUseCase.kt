package com.gyub.core.domain.usecase

import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 영화 상세 가져오는 UseCase
 *
 * @author   Gyub
 * @created  2024/08/18
 */
class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): Flow<MovieDetailModel> = repository.getMovieDetail(movieId)

}