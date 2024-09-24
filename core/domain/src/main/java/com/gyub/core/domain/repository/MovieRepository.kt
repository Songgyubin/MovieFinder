package com.gyub.core.domain.repository

import androidx.paging.PagingData
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

/**
 * 영화 Repository
 *
 * @author   Gyub
 * @created  2024/08/05
 */
interface MovieRepository {
    suspend fun getMovies(orderBy: String): Flow<List<MovieModel>>

    suspend fun getMovieDetail(movieId: Int): MovieDetailModel

    fun getBookmarkedMovies(): Flow<PagingData<MovieModel>>

    suspend fun bookmarkMovie(movie: MovieModel, bookmark: Boolean)

    suspend fun getBookmarkedMovieIds(): Flow<List<Int>>

    suspend fun getMovieCredits(movieId: Int): MovieCreditsModel

    suspend fun getSimilarMovies(page: Int, movieId: Int): List<MovieModel>

    suspend fun getRecommendationsMovies(page: Int, movieId: Int): List<MovieModel>
}