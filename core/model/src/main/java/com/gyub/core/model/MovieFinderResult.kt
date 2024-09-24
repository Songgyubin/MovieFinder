package com.gyub.core.model

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.IOException

/**
 * 공통 Result 모델
 *
 * @author   Gyub
 * @created  2024/09/06
 */
sealed interface MovieFinderResult<out T> {
    data class Success<T>(val data: T) : MovieFinderResult<T>
    data class Error(val exception: RootError) : MovieFinderResult<Nothing>
    data object Loading : MovieFinderResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<MovieFinderResult<T>> = map<T, MovieFinderResult<T>> { MovieFinderResult.Success(it) }
    .onStart { emit(MovieFinderResult.Loading) }
    .catch {
        Log.e("MovieFinder", "asResult: ${it.message}")
        emit(MovieFinderResult.Error(it.toRootError()))
    }

private fun Throwable.toRootError(): RootError {
    return when (this) {
        is IOException -> NetworkError.NO_INTERNET
        else -> RootError.UNKNOWN
    }
}