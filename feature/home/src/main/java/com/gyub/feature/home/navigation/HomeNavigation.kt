package com.gyub.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.core.navigation.MainTabRoute
import com.gyub.feature.home.HomeRoute

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