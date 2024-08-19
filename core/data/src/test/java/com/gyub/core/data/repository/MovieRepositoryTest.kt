package com.gyub.core.data.repository

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/18
 */
class MovieRepositoryTest {

    private val movieRepository = MovieRepositoryImpl(
        dataSource = FakeMovieDataSource(),
        bookmarkDao = FakeBookmarkDao()
    )
}