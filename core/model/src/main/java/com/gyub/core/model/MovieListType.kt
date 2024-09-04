package com.gyub.core.model

/**
 * 영화 리스트 종류
 *
 * @author   Gyub
 * @created  2024/09/03
 */
enum class MovieListType(val orderBy: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming")
}