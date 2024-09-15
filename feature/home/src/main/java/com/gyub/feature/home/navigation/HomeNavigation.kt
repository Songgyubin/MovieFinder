package com.gyub.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
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
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    composable<MainTabRoute.HOME> {
        HomeRoute(
            innerPadding = innerPadding,
            navigateMovieDetail = navigateMovieDetail
        )
    }

    movieDetailScreen(
        innerPadding = innerPadding,
        onBackClick = onBackClick
    )
}