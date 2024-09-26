package com.gyub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.gyub.core.data.datasource.remote.MovieRemoteDataSource
import com.gyub.core.data.model.toDomainModel
import com.gyub.core.data.model.toEntity
import com.gyub.core.db.dao.BookmarkDao
import com.gyub.core.db.model.MovieEntity
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import com.gyub.core.network.model.base.BaseMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Movie Repository 구현체
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieRemoteDataSource,
    private val bookmarkDao: BookmarkDao,
) : MovieRepository {

    override suspend fun getMovies(orderBy: String): Flow<List<MovieModel>> = flowOf(dataSource.getMovies(orderBy = orderBy, page = 1).results.map(BaseMovieResponse::toDomainModel))
        .combine(bookmarkDao.getBookmarkedMovieIds()) { movies, bookmarkedMovieIds ->
            movies.map { movie ->
                movie.copy(isBookmarked = bookmarkedMovieIds.contains(movie.id))
            }
        }

    override suspend fun getMovieDetail(movieId: Int): Flow<MovieDetailModel> {
        val movieDetail = dataSource.getMovieDetail(movieId).toDomainModel()
        val bookmarkedMovieIds = bookmarkDao.getBookmarkedMovieIds()

        return combine(flowOf(movieDetail), bookmarkedMovieIds) { detail, ids ->
            detail.copy(
                isBookmarked = ids.contains(movieDetail.id)
            )
        }
    }


    override suspend fun getMovieCredits(movieId: Int): MovieCreditsModel =
        dataSource.getMovieCredits(movieId).toDomainModel()

    override suspend fun getSimilarMovies(page: Int, movieId: Int): List<MovieModel> =
        dataSource.getSimilarMovies(movieId = movieId, page = 1).results.map(BaseMovieResponse::toDomainModel)

    override suspend fun getRecommendationsMovies(page: Int, movieId: Int): List<MovieModel> =
        dataSource.getRecommendationsMovies(movieId = movieId, page = 1).results.map(BaseMovieResponse::toDomainModel)

    override fun getBookmarkedMovies(): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { bookmarkDao.getBookmarkedMovies() }
    ).flow.map { pagingData -> pagingData.map(MovieEntity::toDomainModel) }

    override suspend fun bookmarkMovie(movie: MovieModel, bookmark: Boolean) {
        if (bookmark) {
            bookmarkDao.insertBookmarkedMovie(movie.copy(isBookmarked = true).toEntity())
        } else {
            bookmarkDao.deleteBookmarkedMovie(movie.id)
        }
    }

    override suspend fun getBookmarkedMovieIds(): Flow<List<Int>> {
        return bookmarkDao.getBookmarkedMovieIds()
    }
}