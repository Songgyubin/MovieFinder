package com.gyub.feature.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gyub.feature.bookmark.BookmarkRoute
import com.gyub.feature.detail.navigation.movieDetailScreen

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
fun NavController.navigateoToBookmark(navOptions: NavOptions) = navigate(com.gyub.core.navigation.MainTabRoute.BOOKMARK, navOptions)

fun NavGraphBuilder.bookmarkScreen(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<com.gyub.core.navigation.MainTabRoute.BOOKMARK> {
        BookmarkRoute(
            onShowErrorSnackBar = onShowErrorSnackBar,
            navigateMovieDetail = navigateMovieDetail
        )
    }

    movieDetailScreen(
        onBackClick = onBackClick
    )
}