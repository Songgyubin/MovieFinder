package com.gyub.core.data_test.datasource

import com.gyub.core.data.datasource.MovieDataSource
import com.gyub.core.network.model.MovieCreditsResponse
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import javax.inject.Inject

/**
 * 테스트를 위한 MovieService
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@OptIn(ExperimentalSerializationApi::class)
class FakeMovieDataSource @Inject constructor(
    private val json: Json = Json { ignoreUnknownKeys = true },
) : MovieDataSource {
    private val movies by lazy { File("src/main/resources/assets/movies.json") }
    private val movieDetail by lazy { File("src/main/resources/assets/movieDetail.json") }
    private val movieCredits by lazy { File("src/main/resources/assets/movieCredits.json") }

    override suspend fun getMovies(page: Int, orderBy: String): MovieListResponse =
        json.decodeFromStream(movies.inputStream())

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse =
        json.decodeFromStream(movieDetail.inputStream())

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse =
        json.decodeFromStream(movieCredits.inputStream())

}