package com.gyub.feature.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.gyub.core.design.component.EmptyView
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.component.RetryButton
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.util.size.PosterSize
import com.gyub.core.domain.model.MovieModel
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * 북마크 화면
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    navigateMovieDetail: (Int) -> Unit,
) {
    val bookmarkedMovies = viewModel.bookmarkedMovies.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceDefault)
            .systemBarsPadding()
            .padding(bottom = innerPadding.calculateBottomPadding())
    ) {
        BookmarkContent(
            bookmarkedMovies = bookmarkedMovies,
            onDeleteBookmarkedMovie = viewModel::onDeleteBookmarkedMovie,
            notifyErrorMessage = viewModel::notifyErrorMessage,
            navigateMovieDetail = navigateMovieDetail
        )
    }
}

@Composable
fun BookmarkContent(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    BookmarkScreen(
        bookmarkedMovies = bookmarkedMovies,
        onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
        notifyErrorMessage = notifyErrorMessage,
        navigateMovieDetail = navigateMovieDetail
    )
}

@Composable
fun BookmarkScreen(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BookmarkTopAppBar()

        LoadStateHandler(
            bookmarkedMovies = bookmarkedMovies,
            onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
            notifyErrorMessage = notifyErrorMessage,
            navigateMovieDetail = navigateMovieDetail
        )
    }
}

@Composable
fun LoadStateHandler(
    bookmarkedMovies: LazyPagingItems<MovieModel>,
    onDeleteBookmarkedMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (Throwable) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    when {
        bookmarkedMovies.loadState.append is LoadState.NotLoading &&
                bookmarkedMovies.loadState.append.endOfPaginationReached &&
                bookmarkedMovies.itemCount == 0 -> {
            EmptyView(
                modifier = Modifier.fillMaxSize(),
                emptyText = R.string.feature_bookmark_empty_bookmark
            )
        }

        bookmarkedMovies.loadState.refresh is LoadState.Loading -> {
            LoadingIndicator()
        }

        bookmarkedMovies.loadState.refresh is LoadState.Error -> {
            notifyErrorMessage((bookmarkedMovies.loadState.refresh as LoadState.Error).error)
            RetryButton(
                modifier = Modifier.fillMaxSize(),
                retryMessage = com.gyub.core.common.R.string.core_common_retry,
                onRetry = { bookmarkedMovies.retry() }
            )
        }

        bookmarkedMovies.loadState.refresh is LoadState.NotLoading -> {
            BookmarkedImageList(
                bookmarkedMovies = bookmarkedMovies,
                onDeleteBookmarkedMovie = onDeleteBookmarkedMovie,
                notifyErrorMessage = notifyErrorMessage,
                navigateMovieDetail = navigateMovieDetail
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
    navigateMovieDetail: (Int) -> Unit,
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
                        onClick = { navigateMovieDetail(movie.id) },
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
                            retryMessage = com.gyub.core.common.R.string.core_common_retry,
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
            .aspectRatio(0.7f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TMDBAsyncImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = movie.posterUrl,
                tmdbImageSize = PosterSize.W342,
                contentScale = ContentScale.FillBounds,
                contentDescription = "Movie Poster",
            )

            IconButton(
                onClick = { onDeleteBookmarkedMovie(movie) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = if (movie.isBookmarked) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = "Bookmark"
                )
            }
        }
    }
}