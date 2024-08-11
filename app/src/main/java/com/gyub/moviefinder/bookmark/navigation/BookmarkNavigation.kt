package com.gyub.moviefinder.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.moviefinder.bookmark.BookmarkRoute
import com.gyub.core.navigation.MainTabRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateoToBookmark(navOptions: NavOptions) = navigate(com.gyub.core.navigation.MainTabRoute.BOOKMARK, navOptions)

fun NavGraphBuilder.bookmarkScreen(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<com.gyub.core.navigation.MainTabRoute.BOOKMARK> {
        BookmarkRoute(onShowErrorSnackBar = onShowErrorSnackBar)
    }
}