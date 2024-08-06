package com.gyub.core.domain.usecase

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * 북마크 UseCase
 *
 * @author   Gyub
 * @created  2024/08/06
 */
class BookmarkMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend operator fun invoke(movie: MovieModel, bookmark: Boolean) {
        movieRepository.bookmarkMovie(movie, bookmark)
    }
}