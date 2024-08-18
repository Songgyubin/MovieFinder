package com.gyub.core.network.retrofit

import com.gyub.core.network.const.Http
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 영화 Service
 *
 * @author   Gyub
 * @created  2024/08/05
 */
interface MovieService {

    @GET("${Http.Api.VERSION}/movie/{orderBy}")
    suspend fun getMovies(
        @Path("orderBy") orderBy: String,
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int,
    ): MovieListResponse

    @GET("${Http.Api.VERSION}/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "ko-KR",
    ): MovieDetailResponse
}