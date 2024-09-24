package com.gyub.core.domain.usecase

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import com.gyub.core.model.MovieFinderResult
import com.gyub.core.model.asResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 영화 리스트 가져오는 UseCase
 *
 * @author   Gyub
 * @created  2024/08/06
 */
class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(orderBy: String): Flow<MovieFinderResult<List<MovieModel>>> =
        repository.getMovies(orderBy = orderBy).asResult()
}