package com.gyub.core.navigation

import kotlinx.serialization.Serializable

/**
 * 메인 탭 루트
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