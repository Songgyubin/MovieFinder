package com.gyub.moviefinder.navigator

import androidx.compose.runtime.Composable
import com.gyub.core.navigation.MainTabRoute
import com.gyub.moviefinder.R

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
enum class MainTab(
    val titleTextId: Int,
    val route: MainTabRoute,
) {
    HOME(
        titleTextId = R.string.home,
        route = MainTabRoute.HOME
    ),
    BOOKMARK(
        titleTextId = R.string.bookmark,
        route = MainTabRoute.BOOKMARK
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }
    }
}