package com.gyub.core.model

/**
 * 공통 Result 모델
 *
 * @author   Gyub
 * @created  2024/09/06
 */
sealed interface Result<out D, out E> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E>(val error: E) : Result<Nothing, E>
}

inline fun <reified D, reified R, reified E> Result<D, E>.convert(callback: (data: D) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(callback(data))
        is Result.Error -> Result.Error(this.error)
    }
}