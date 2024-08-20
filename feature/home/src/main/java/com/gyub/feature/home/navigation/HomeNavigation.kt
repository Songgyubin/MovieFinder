package com.gyub.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.core.navigation.MainTabRoute
import com.gyub.feature.detail.navigation.movieDetailScreen
import com.gyub.feature.home.HomeRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(MainTabRoute.HOME, navOptions)

fun NavGraphBuilder.homeScreen(
    onBackClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    composable<MainTabRoute.HOME> {
        HomeRoute(
            onShowErrorSnackBar = onShowErrorSnackBar,
            navigateMovieDetail = navigateMovieDetail
        )
    }

    movieDetailScreen(
        onBackClick = onBackClick
    )
}