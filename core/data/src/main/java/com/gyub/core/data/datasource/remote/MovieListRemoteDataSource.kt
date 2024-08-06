package com.gyub.core.data.datasource.remote

import com.gyub.core.network.model.MovieListResponse
import com.gyub.core.network.retrofit.MovieService
import javax.inject.Inject

/**
 * 영화 리스트 원격 DataSource
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class MovieListRemoteDataSource @Inject constructor(
    private val service: MovieService,
) {
    // TODO: BaseDataSource 통해 에러 캐칭
    suspend fun getMovies(page: Int, orderBy: String): MovieListResponse =
        service.getMovies(orderBy = orderBy, page = page)
}