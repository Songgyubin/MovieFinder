package com.gyub.core.db

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.gyub.core.db.dao.BookmarkDao
import com.gyub.core.db.model.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * 북마크 테스트
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BookmarkDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var bookmarkDao: BookmarkDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        bookmarkDao = database.bookmarkDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private val testMovie = MovieEntity(
        id = 1,
        title = "testTitle",
        posterUrl = "https://testUrl.com",
        voteAverage = 6.7,
        overview = "test overview"
    )

    @Test
    fun insertAndGetBookmarkedImage() = runTest {
        bookmarkDao.insertBookmarkedMovie(testMovie)

        val pagingSource = bookmarkDao.getBookmarkedMovies()

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(
            expected = listOf(testMovie),
            actual = result.data
        )
    }

    @Test
    fun deleteBookmarkedImage() = runTest {
        bookmarkDao.insertBookmarkedMovie(testMovie)
        bookmarkDao.deleteBookmarkedMovie(testMovie.id)

        val pagingSource = bookmarkDao.getBookmarkedMovies()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(
            expected = emptyList(),
            actual = result.data
        )
    }

    @Test
    fun getBookmarkedImageIds() = runTest {
        val imageEntity1 = testMovie
        val imageEntity2 = testMovie.copy(id = 2)
        bookmarkDao.insertBookmarkedMovie(imageEntity1)
        bookmarkDao.insertBookmarkedMovie(imageEntity2)

        val imageIds = bookmarkDao.getBookmarkedMovieIds().first()

        assertEquals(
            expected = listOf(1, 2),
            actual = imageIds
        )
    }
}