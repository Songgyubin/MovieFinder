package com.gyub.feature.bookmark.navigation

import androidx.compose.foundation.layout.PaddingValues
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
fun NavController.navigateToBookmark(navOptions: NavOptions) = navigate(com.gyub.core.navigation.MainTabRoute.BOOKMARK, navOptions)

fun NavGraphBuilder.bookmarkScreen(
    innerPadding:PaddingValues,
    navigateMovieDetail: (Int) -> Unit,
    onBackClick: () -> Unit,

) {
    composable<com.gyub.core.navigation.MainTabRoute.BOOKMARK> {
        BookmarkRoute(
            innerPadding = innerPadding,
            navigateMovieDetail = navigateMovieDetail
        )
    }

    movieDetailScreen(
        innerPadding = innerPadding,
        onBackClick = onBackClick,
    )
}