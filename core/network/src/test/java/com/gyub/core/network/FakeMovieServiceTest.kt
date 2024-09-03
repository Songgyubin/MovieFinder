package com.gyub.core.network

import com.gyub.core.network.fake.FakeMovieService
import com.gyub.core.network.retrofit.MovieService
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

/**
 * MovieService Test Code
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class FakeMovieServiceTest {

    private val movieService: MovieService = FakeMovieService()

    @Test
    fun getMovies() = runTest {
        val movies = movieService.getMovies("popular", "ko-KR", 1)
        println(movies)

        assert(movies.totalPages == 45391)
        assert(movies.results.isNotEmpty())
        assertEquals(
            expected = "콰이어트 플레이스: 첫째 날",
            actual = movies.results.first().title,
        )
    }

    @Test
    fun getMovieDetail() = runTest {
        val movieDetail = movieService.getMovieDetail(533535, "ko-KR")
        println(movieDetail)

        assertEquals(
            expected = "데드풀과 울버린",
            actual = movieDetail.title
        )
    }

    @Test
    fun getMovieCredits() = runTest {
        val movieCredits = movieService.getMovieCredits(533535, "ko-KR")
        println(movieCredits)

        assertEquals(
            expected = "라이언 레이놀즈",
            actual = movieCredits.cast.first().name
        )

        assertEquals(
            expected = "숀 레비",
            actual = movieCredits.crew.find { it.job == "Director" }?.name
        )
    }

    @Test
    fun getSimilarMovies() = runTest {
        val similarMovies = movieService.getSimilarMovies(movieId = 573435, language = "ko-KR", page = 1)
        println(similarMovies)

        assertEquals(
            expected = "환타즘: 래비저",
            actual = similarMovies.results.first().title
        )
    }
}