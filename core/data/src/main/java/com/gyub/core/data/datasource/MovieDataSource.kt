package com.gyub.core.data.datasource

import com.gyub.core.network.model.MovieCreditsResponse
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse

/**
 * 영화 데이터 소스
 *
 * @author   Gyub
 * @created  2024/08/18
 */
interface MovieDataSource {

    suspend fun getMovies(page: Int, orderBy: String): MovieListResponse

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse

    suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse
}