package com.gyub.feature.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.core.navigation.Route
import com.gyub.feature.detail.MovieDetailRoute

/**
 * 영화 상세 네비게이션
 *
 * @author   Gyub
 * @created  2024/08/21
 */
fun NavController.navigateToMovieDetail(movieId: Int) = navigate(
    Route.MovieDetail(
        movieId = movieId,
    )
)

fun NavGraphBuilder.movieDetailScreen(
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
) {
    composable<Route.MovieDetail> { _ ->
        MovieDetailRoute(
            innerPadding = innerPadding,
            onBackClick = onBackClick
        )
    }
}