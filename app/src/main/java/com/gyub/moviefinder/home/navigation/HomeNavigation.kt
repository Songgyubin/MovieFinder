package com.gyub.moviefinder.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.moviefinder.home.HomeRoute
import com.gyub.core.navigation.MainTabRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(com.gyub.core.navigation.MainTabRoute.HOME, navOptions)

fun NavGraphBuilder.homeScreen(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<com.gyub.core.navigation.MainTabRoute.HOME> {
        HomeRoute(onShowErrorSnackBar)
    }
}