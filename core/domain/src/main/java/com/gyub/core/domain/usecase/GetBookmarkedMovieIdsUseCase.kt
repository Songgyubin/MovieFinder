package com.gyub.core.domain.usecase

import com.gyub.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 북마크 된 영화의 id 리스트 가져오는 UseCase
 *
 * @author   Gyub
 * @created  2024/08/06
 */
class GetBookmarkedMovieIdsUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend operator fun invoke(): Flow<List<Int>> = repository.getBookmarkedMovieIds()
}