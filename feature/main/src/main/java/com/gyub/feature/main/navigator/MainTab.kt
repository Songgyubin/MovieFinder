package com.gyub.feature.main.navigator

import androidx.compose.runtime.Composable
import com.gyub.core.navigation.MainTabRoute
import com.gyub.core.navigation.Route
import com.gyub.feature.main.R

/**
 * 메인 탭
 *
 * @author   Gyub
 * @created  2024/08/06
 */
enum class MainTab(
    val titleTextId: Int,
    val route: MainTabRoute,
) {
    HOME(
        titleTextId = R.string.feature_main_home,
        route = MainTabRoute.HOME
    ),
    BOOKMARK(
        titleTextId = R.string.feature_main_bookmark,
        route = MainTabRoute.BOOKMARK
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}