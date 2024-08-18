package com.gyub.core.domain.usecase

import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 영화 크레딧 가져오는 UseCase
 *
 * @author   Gyub
 * @created  2024/08/18
 */
class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository,
) {

    suspend operator fun invoke(movieId: Int): Flow<MovieCreditsModel> = flow {
        emit(repository.getMovieCredits(movieId))
    }
}