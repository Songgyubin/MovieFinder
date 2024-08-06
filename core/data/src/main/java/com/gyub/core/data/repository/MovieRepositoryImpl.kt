package com.gyub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.gyub.core.data.datasource.MovieListPagingSource
import com.gyub.core.data.datasource.remote.MovieListRemoteDataSource
import com.gyub.core.data.model.toDomainModel
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.repository.MovieRepository
import com.gyub.core.network.model.MovieListResponse
import kotlinx.coroutines.flow.Flow
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
    private val dataSource: MovieListRemoteDataSource,
) : MovieRepository {

    override fun getMovies(orderBy: String): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MovieListPagingSource(
                remoteDataSource = dataSource,
                orderBy = orderBy
            )
        }
    ).flow.map { pagingData -> pagingData.map(MovieListResponse.Movie::toDomainModel) }
}