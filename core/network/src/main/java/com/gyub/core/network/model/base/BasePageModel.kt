package com.gyub.core.network.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 *
 * @author   Gyub
 * @created  2024/09/03
 */
@Serializable
open class BasePageModel(
    val page: Int = 1,
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Long = 0,
)