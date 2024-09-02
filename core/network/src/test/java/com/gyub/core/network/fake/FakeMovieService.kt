package com.gyub.core.network.fake

import com.gyub.core.network.model.MovieCreditsResponse
import com.gyub.core.network.model.MovieDetailResponse
import com.gyub.core.network.model.MovieListResponse
import com.gyub.core.network.model.MovieSimilarResponse
import com.gyub.core.network.retrofit.MovieService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

/**
 * 테스트를 위한 MovieService
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@OptIn(ExperimentalSerializationApi::class)
class FakeMovieService(
    private val json: Json = Json { ignoreUnknownKeys = true },
) : MovieService {
    private val movies by lazy { File("src/test/resources/assets/movies.json") }
    private val movieDetail by lazy { File("src/test/resources/assets/movieDetail.json") }
    private val movieCredits by lazy { File("src/test/resources/assets/movieCredits.json") }
    private val similarMovies by lazy { File("src/test/resources/assets/similarMovies.json") }

    override suspend fun getMovies(orderBy: String, language: String, page: Int): MovieListResponse =
        json.decodeFromStream(movies.inputStream())


    override suspend fun getMovieDetail(movieId: Int, language: String): MovieDetailResponse =
        json.decodeFromStream(movieDetail.inputStream())

    override suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsResponse =
        json.decodeFromStream(movieCredits.inputStream())

    override suspend fun getSimilarMovies(movieId: Int, language: String, page: Int): MovieSimilarResponse =
        json.decodeFromStream(similarMovies.inputStream())
}