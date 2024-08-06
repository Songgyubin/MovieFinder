package com.gyub.moviefinder.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.gyub.core.common.tmdb.size.PosterSize
import com.gyub.core.domain.model.MovieModel
import com.gyub.moviefinder.R
import com.gyub.moviefinder.design.component.EmptyView
import com.gyub.moviefinder.design.component.LoadingIndicator
import com.gyub.moviefinder.design.component.PosterAsyncImage
import com.gyub.moviefinder.design.component.RetryButton
import kotlinx.coroutines.flow.collectLatest

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun BookmarkRoute(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    val bookmarkedMovies = viewModel.bookmarkedMovies.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { onShowErrorSnackBar(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .systemBarsPadding()
            .padding(bottom = 56.dp)
    ) {
        BookmarkContent(
            bookmarkedMovies = bookmarkedMovies,
            onDeleteBookmarkedMovie = viewModel::onDeleteBookmarkedMovie,
            notifyErrorMessage = viewModel::notifyErrorMessage
        )
    }
}

@Composable
fun BookmarkContent(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
) {

    BookmarkScreen(
        bookmarkedMovies = bookmarkedMovies,
        onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
        notifyErrorMessage = notifyErrorMessage
    )
}

@Composable
fun BookmarkScreen(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        BookmarkTopAppBar()

        LoadStateHandler(
            bookmarkedMovies = bookmarkedMovies,
            onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
            notifyErrorMessage = notifyErrorMessage
        )
    }
}

@Composable
fun LoadStateHandler(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
) {
    when {
        bookmarkedMovies.loadState.append is LoadState.NotLoading &&
                bookmarkedMovies.loadState.append.endOfPaginationReached &&
                bookmarkedMovies.itemCount == 0 -> {
            EmptyView(
                modifier = Modifier.fillMaxSize(),
                emptyText = R.string.empty_bookmark
            )
        }

        bookmarkedMovies.loadState.refresh is LoadState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        bookmarkedMovies.loadState.refresh is LoadState.Error -> {
            notifyErrorMessage((bookmarkedMovies.loadState.refresh as LoadState.Error).error)
            RetryButton(
                modifier = Modifier.fillMaxSize(),
                onRetry = { bookmarkedMovies.retry() }
            )
        }

        bookmarkedMovies.loadState.refresh is LoadState.NotLoading -> {
            BookmarkedImageList(
                bookmarkedMovies = bookmarkedMovies,
                onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
                notifyErrorMessage = notifyErrorMessage,
            )
        }
    }
}

@Composable
fun BookmarkedImageList(
    modifier: Modifier = Modifier,
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(
            count = bookmarkedMovies.itemCount,
            key = bookmarkedMovies.itemKey { it.id }
        ) { index ->
            val movie = bookmarkedMovies[index]!!

            Column(
                modifier = Modifier
                    .clickable(
                        onClick = { onDeleteBookmarkedMovie(movie) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
                BookmarkedImageCard(
                    movie = movie,
                    onDeleteBookmarkedMovie = onDeleteBookmarkedMovie
                )
            }
        }

        bookmarkedMovies.apply {
            when (val loadState = loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> {
                    item {
                        LoadingIndicator(modifier = modifier.fillMaxSize())
                    }
                }

                is LoadState.Error -> {
                    notifyErrorMessage(loadState.error)

                    item {
                        RetryButton(
                            modifier = modifier.fillMaxWidth(),
                            onRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarkedImageCard(
    movie: MovieModel,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PosterAsyncImage(
                imageUrl = movie.posterUrl,
                tmdbImageSize = PosterSize.W154,
                contentDescription = stringResource(R.string.description_bookmarked_movie_poster),
            )

            IconButton(
                onClick = { onDeleteBookmarkedMovie(movie) },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = if (movie.isBookmarked) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.description_is_bookmarked)
                )
            }
        }
    }
}