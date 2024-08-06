package com.gyub.moviefinder.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.moviefinder.home.HomeRoute
import com.gyub.moviefinder.navigator.MainTabRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(MainTabRoute.HOME, navOptions)

fun NavGraphBuilder.homeScreen(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<MainTabRoute.HOME> {
        HomeRoute(onShowErrorSnackBar)
    }
}