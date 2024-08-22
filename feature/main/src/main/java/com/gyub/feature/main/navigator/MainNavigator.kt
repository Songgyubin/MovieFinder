package com.gyub.feature.main.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.gyub.core.navigation.MainTabRoute
import com.gyub.core.navigation.Route
import com.gyub.feature.bookmark.navigation.navigateToBookmark
import com.gyub.feature.detail.navigation.navigateToMovieDetail
import com.gyub.feature.home.navigation.navigateToHome

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.HOME.route

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.BOOKMARK -> navController.navigateToBookmark(navOptions)
        }
    }

    fun navigateMovieDetail(movieId: Int) {
        navController.navigateToMovieDetail(movieId = movieId)
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination<MainTabRoute.HOME>()) {
            popBackStack()
        }
    }

    @Composable
    fun shouldShowBottomBar(): Boolean = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}