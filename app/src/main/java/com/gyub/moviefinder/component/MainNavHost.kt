package com.gyub.moviefinder.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.gyub.feature.home.navigation.homeScreen
import com.gyub.moviefinder.bookmark.navigation.bookmarkScreen
import com.gyub.moviefinder.navigator.MainNavigator

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun MainNavHost(
    navigator: MainNavigator,
    innerPadding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeScreen(
            onShowErrorSnackBar = onShowErrorSnackBar,
        )

        bookmarkScreen(
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}