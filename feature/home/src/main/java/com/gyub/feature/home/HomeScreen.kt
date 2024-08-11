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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.gyub.core.common.extensions.formatToSingleDecimal
import com.gyub.core.design.component.EmptyView
import com.gyub.core.domain.model.MovieModel

/**
 * 홈 화면
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun HomeRoute(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect(onShowErrorSnackBar)
    }

    val movies = viewModel.movies.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .systemBarsPadding()
            .padding(bottom = 56.dp)
    ) {
        HomeScreen(
            movies = movies,
            onBookmarkMovie = viewModel::onBookmarkMovie,
            notifyErrorMessage = viewModel::notifyErrorMessage
        )
    }
}

@Composable
fun HomeScreen(
    movies: LazyPagingItems<MovieModel>,
    notifyErrorMessage: (Throwable) -> Unit,
    onBookmarkMovie: (MovieModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        LoadStateHandler(
            movies = movies,
            onBookmarkMovie = onBookmarkMovie,
            notifyErrorMessage = notifyErrorMessage
        )
    }
}

@Composable
fun LoadStateHandler(
    movies: LazyPagingItems<MovieModel>,
    notifyErrorMessage: (Throwable) -> Unit,
    onBookmarkMovie: (MovieModel) -> Unit,
) {
    when {
        movies.loadState.append is LoadState.NotLoading &&
                movies.loadState.append.endOfPaginationReached &&
                movies.itemCount == 0 -> {
            EmptyView(
                modifier = Modifier.fillMaxSize(),
                emptyText = com.gyub.core.common.R.string.common_empty_result
            )
        }

        movies.loadState.refresh is LoadState.Loading -> {
            com.gyub.core.design.component.LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        movies.loadState.refresh is LoadState.Error -> {
            notifyErrorMessage((movies.loadState.refresh as LoadState.Error).error)
            com.gyub.core.design.component.RetryButton(
                modifier = Modifier.fillMaxSize(),
                retryMessage = com.gyub.core.common.R.string.common_retry,
                onRetry = { movies.retry() }
            )
        }

        movies.loadState.refresh is LoadState.NotLoading -> {
            MovieList(
                movies = movies,
                onBookmarkMovie = onBookmarkMovie,
                notifyErrorMessage = notifyErrorMessage
            )
        }
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieModel>,
    notifyErrorMessage: (Throwable) -> Unit,
    onBookmarkMovie: (MovieModel) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp),
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id }
        ) { index ->
            MovieCard(
                movie = movies[index]!!,
                onBookmarkMovie = onBookmarkMovie
            )
        }

        movies.apply {
            when (val loadState = loadState.append) {
                is LoadState.NotLoading -> {}

                is LoadState.Loading -> {
                    item {
                        com.gyub.core.design.component.LoadingIndicator(modifier = modifier.fillMaxSize())
                    }
                }

                is LoadState.Error -> {
                    notifyErrorMessage(loadState.error)

                    item {
                        com.gyub.core.design.component.RetryButton(
                            modifier = modifier.fillMaxWidth(),
                            retryMessage = com.gyub.core.common.R.string.common_retry,
                            onRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieModel,
    onBookmarkMovie: (MovieModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
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

        com.gyub.core.design.component.PosterAsyncImage(
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
        title = "viris",
        posterUrl = "https://www.google.com/#q=vituperatoribus",
        voteAverage = 2.3,
        overview = "non"
    )
    com.gyub.core.design.theme.MovieFinderTheme {
        MovieCard(
            movie = movie,
            onBookmarkMovie = {},
        )
    }
}