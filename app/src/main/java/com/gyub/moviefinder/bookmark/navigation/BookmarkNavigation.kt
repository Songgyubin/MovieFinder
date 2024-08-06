package com.gyub.moviefinder.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.moviefinder.bookmark.BookmarkRoute
import com.gyub.moviefinder.navigator.MainTabRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateoToBookmark(navOptions: NavOptions) = navigate(MainTabRoute.BOOKMARK, navOptions)

fun NavGraphBuilder.bookmarkScreen(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<MainTabRoute.BOOKMARK> {
        BookmarkRoute(onShowErrorSnackBar = onShowErrorSnackBar)
    }
}