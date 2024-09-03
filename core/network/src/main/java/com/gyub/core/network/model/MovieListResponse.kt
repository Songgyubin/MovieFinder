package com.gyub.core.network.model

import com.gyub.core.network.model.base.BaseMovieResponse
import com.gyub.core.network.model.base.BasePageModel
import kotlinx.serialization.Serializable

/**
 * 영화 리스트 응답 모델
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@Serializable
data class MovieListResponse(
    val results: List<BaseMovieResponse>,
) : BasePageModel()