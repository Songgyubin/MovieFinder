package com.gyub.core.data.datasource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gyub.core.data.datasource.remote.MovieRemoteDataSource
import com.gyub.core.network.model.MovieResponse
import java.io.IOException

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/05
 */
class MovieListPagingSource(
    private val remoteDataSource: MovieRemoteDataSource,
    private val orderBy: String,
) : PagingSource<Int, MovieResponse>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
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

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}