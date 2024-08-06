package com.gyub.core.domain.usecase

import androidx.paging.PagingData
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
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
    operator fun invoke(orderBy: String): Flow<PagingData<MovieModel>> =
        repository.getMovies(orderBy = orderBy)
}