package com.gyub.core.data.datasource.remote

import com.gyub.core.network.model.MovieCreditsResponse
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse
import com.gyub.core.network.model.MovieSimilarResponse
import com.gyub.core.network.retrofit.MovieService
import javax.inject.Inject

/**
 * 영화 리스트 원격 DataSource
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService,
) {
    // TODO: BaseDataSource 통해 에러 캐칭
    suspend fun getMovies(page: Int, orderBy: String): MovieListResponse =
        service.getMovies(orderBy = orderBy, page = page)

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse =
        service.getMovieDetail(movieId)

    suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse =
        service.getMovieCredits(movieId)

    suspend fun getSimilarMovies(page: Int = 1, movieId: Int): MovieSimilarResponse =
        service.getSimilarMovies(movieId = movieId, page = page)
}