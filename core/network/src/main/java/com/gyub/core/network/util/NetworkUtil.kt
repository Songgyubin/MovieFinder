package com.gyub.core.network.util

import com.gyub.core.network.BuildConfig
import com.gyub.core.network.const.Http.Headers.ACCEPT
import com.gyub.core.network.const.Http.Headers.AUTHORIZATION
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.HttpUrl
import okhttp3.Interceptor

/**
 * 네트워크 관련 유틸
 *
 * @author   Gyub
 * @created  2024/08/05
 */
internal object NetworkUtil {

    fun createQuery() =
        Interceptor { chain: Interceptor.Chain ->
            val originalRequest = chain.request()
            val originalHttpUrl: HttpUrl = originalRequest.url

            val urlWithApiKey = originalHttpUrl.newBuilder()
                .addQueryParameter("region", "KR")
                .build()

            val requestWithApiKey = originalRequest.newBuilder()
                .url(urlWithApiKey)
                .build()

            chain.proceed(requestWithApiKey)
        }

    fun createHeader() =
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                .addHeader(AUTHORIZATION, BuildConfig.TMDB_API_KEY)
                .addHeader(ACCEPT, "application/json")
                .build()

            chain.proceed(builder)
        }

    fun getPrettyLog(text: String?): String {
        text ?: return ""

        return try {
            val json = Json { prettyPrint = true }
            val jsonElement = json.decodeFromString<JsonObject>(text)
            json.encodeToString(JsonObject.serializer(), jsonElement)
        } catch (e: Exception) {
            text
        }
    }
}