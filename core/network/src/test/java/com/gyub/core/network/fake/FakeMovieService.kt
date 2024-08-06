package com.gyub.core.network.fake

import com.gyub.core.network.model.MovieListResponse
import com.gyub.core.network.retrofit.MovieService
import kotlinx.serialization.json.Json
import java.io.File

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class FakeMovieService : MovieService {
    override suspend fun getMovies(orderBy: String, language: String, page: Int): MovieListResponse {
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource("assets/movies.json").file)
        val jsonString = file.readText()

        return Json.decodeFromString(jsonString)
    }
}