package com.gyub.core.network.const

/**
 * 헤더, URL 등
 * Http 통신에 필요한 상수 정의
 *
 * @author   Gyub
 * @created  2024/08/05
 */
internal object Http {
    object Headers {
        const val AUTHORIZATION = "Authorization"
        const val ACCEPT = "Accept"
    }

    object Url {
        val BASE_URL: String by lazy { "https://api.themoviedb.org" }
    }

    object Api {
        const val VERSION = "3"

        const val MOVIES_NOW_PLAYING = "now_playing"
        const val MOVIES_POPULAR = "popular"
        const val MOVIES_TOP_RATED = "top_rated"
        const val MOVIES_UPCOMING = "upcoming"
    }
}