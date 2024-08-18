package com.gyub.core.data.datasource.remote

import com.gyub.core.data.datasource.MovieDataSource
import com.gyub.core.network.model.MovieCreditsResponse
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse
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
) : MovieDataSource {
    // TODO: BaseDataSource 통해 에러 캐칭
    override suspend fun getMovies(page: Int, orderBy: String): MovieListResponse =
        service.getMovies(orderBy = orderBy, page = page)

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse =
        service.getMovieDetail(movieId)

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse =
        service.getMovieCredits(movieId)
}