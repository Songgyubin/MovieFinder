package com.gyub.core.domain.usecase

import androidx.paging.PagingData
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 북마크된 영화 가져오는 UseCase
 *
 * @author   Gyub
 * @created  2024/08/06
 */
class GetBookmarkedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<MovieModel>> = repository.getBookmarkedMovies()
}