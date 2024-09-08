package com.gyub.core.model

/**
 * 에러 interface
 *
 * @author   Gyub
 * @created  2024/09/06
 */
sealed interface RootError {
    data object UNKNOWN : RootError
}

enum class NetworkError : RootError {
    NO_INTERNET,
    BAD_REQUEST,
    NOT_FOUND,
}