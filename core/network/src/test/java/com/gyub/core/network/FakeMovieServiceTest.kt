package com.gyub.core.network

import com.gyub.core.network.fake.FakeMovieService
import com.gyub.core.network.retrofit.MovieService
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class FakeMovieServiceTest {

    private val movieService: MovieService = FakeMovieService()

    @Test
    fun getMovies() = runTest {
        println(movieService.getMovies("popular", "ko-KR", 1))
    }
}