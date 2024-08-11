package com.gyub.moviefinder.navigator

import kotlinx.serialization.Serializable

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
sealed interface MainTabRoute {
    @Serializable
    data object HOME: MainTabRoute

    @Serializable
    data object BOOKMARK: MainTabRoute
}