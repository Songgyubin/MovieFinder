package com.gyub.core.data.datasource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gyub.core.network.model.MovieListResponse
import java.io.IOException
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class MovieListPagingSource @Inject constructor(
    private val remoteDataSource: MovieDataSource,
    private val orderBy: String,
) : PagingSource<Int, MovieListResponse.Movie>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResponse.Movie> {
        val page = params.key ?: 1

        return try {
            val response = remoteDataSource.getMovies(page = page, orderBy = orderBy)

            val nextKey = if (response.results.isNullOrEmpty()) {
                null
            } else {
                page.plus(1)
            }

            LoadResult.Page(
                data = response.results.orEmpty(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieListResponse.Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}