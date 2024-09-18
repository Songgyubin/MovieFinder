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
        val TMDB_BASE_URL: String by lazy { "https://api.themoviedb.org" }
        val KMDB_SEARCH_BASE_URL: String by lazy { "http://api.koreafilm.or.kr/openapi-data2/wisenut" }
    }

    object Api {
        const val VERSION = "3"
    }
}