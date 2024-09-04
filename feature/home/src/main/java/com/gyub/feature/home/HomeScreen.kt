package com.gyub.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.core.common.extensions.formatToSingleDecimal
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.theme.LightGray300
import com.gyub.core.domain.model.MovieModel
import com.gyub.feature.home.model.MovieUiState

/**
 * 홈 화면
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateMovieDetail: (Int) -> Unit,
) {
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(bottom = 56.dp)
    ) {
        HomeScreen(
            movieUiState = movies,
            onBookmarkMovie = viewModel::onBookmarkMovie,
            notifyErrorMessage = viewModel::notifyErrorMessage,
            navigateMovieDetail = navigateMovieDetail
        )
    }
}

@Composable
fun HomeScreen(
    movieUiState: MovieUiState,
    notifyErrorMessage: (Throwable) -> Unit,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    when (movieUiState) {
        is MovieUiState.Error -> {
            notifyErrorMessage(Throwable(movieUiState.message))
        }

        MovieUiState.Loading -> {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )
        }

        is MovieUiState.Success -> {
            MovieList(
                movies = movieUiState.movies,
                onBookmarkMovie = onBookmarkMovie,
                navigateMovieDetail = navigateMovieDetail
            )
        }
    }
}

@Composable
fun MovieList(
    movies: List<MovieModel>,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp),
    ) {
        itemsIndexed(movies, key = { _, movie -> movie.id }) { index, movie ->
            Column {
                MovieCard(
                    movie = movie,
                    onBookmarkMovie = onBookmarkMovie,
                    navigateMovieDetail = navigateMovieDetail
                )

                if (index < movies.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                        thickness = 1.dp,
                        color = LightGray300
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieModel,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { navigateMovieDetail(movie.id) }
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movie.title,
                    style = com.gyub.core.design.theme.MovieFinderTheme.typography.titleMediumB
                )
                Text(
                    text = stringResource(R.string.feature_home_rating, movie.voteAverage.formatToSingleDecimal()),
                    style = com.gyub.core.design.theme.MovieFinderTheme.typography.labelLargeM
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onBookmarkMovie(movie) }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp),
                    imageVector = if (movie.isBookmarked) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = "Bookmark Icon"
                )
            }
        }

        com.gyub.core.design.component.TMDBAsyncImage(
            imageUrl = movie.posterUrl,
            contentDescription = "Movie Poster",
            tmdbImageSize = com.gyub.core.design.util.size.PosterSize.W185,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = MovieModel(
        id = 9054,
        title = "test title",
        posterUrl = "https://www.google.com/#q=vituperatoribus",
        voteAverage = 2.3,
        overview = "non"
    )
    com.gyub.core.design.theme.MovieFinderTheme {
        MovieCard(
            movie = movie,
            onBookmarkMovie = {},
            navigateMovieDetail = {},
        )
    }
}