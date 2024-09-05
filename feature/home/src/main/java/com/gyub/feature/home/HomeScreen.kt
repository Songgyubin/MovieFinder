package com.gyub.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.theme.MovieFinderTheme
import com.gyub.core.design.util.size.PosterSize
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.model.MovieListType
import com.gyub.feature.home.model.MovieSection
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
            MovieContent(
                movieSections = movieUiState.movieSections,
                onBookmarkMovie = onBookmarkMovie,
                navigateMovieDetail = navigateMovieDetail
            )
        }
    }
}

@Composable
fun MovieContent(
    movieSections: List<MovieSection>,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        movieSections.find { MovieListType.NOW_PLAYING == it.movieListType }?.let {
            item {
                MovieViewPager(
                    movies = it.movies,
                )
            }
        }
        items(movieSections.filter { it.movieListType != MovieListType.NOW_PLAYING }) { section ->
            when (section.movieListType) {
                MovieListType.NOW_PLAYING -> {

                }


                else -> {
                    MovieSection(
                        movies = section.movies,
                        movieListType = section.movieListType,
                        onBookmarkMovie = onBookmarkMovie,
                        navigateMovieDetail = navigateMovieDetail
                    )
                }
            }
        }
    }
}

@Composable
fun MovieSection(
    movies: List<MovieModel>,
    movieListType: MovieListType,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(generateMovieSectionLabel(movieListType)),
        )

        LazyRow {
            items(movies, key = { it.id }) { movie ->
                MovieThumbnailCard(
                    movie = movie,
                    onBookmarkMovie = onBookmarkMovie,
                    navigateMovieDetail = navigateMovieDetail
                )
            }
        }
    }
}

@Composable
fun MovieViewPager(
    movies: List<MovieModel>,
) {
    val pagerState = rememberPagerState(pageCount = { movies.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
    ) { page ->
        val movie = movies[page]
        TMDBAsyncImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = movie.posterUrl,
            tmdbImageSize = PosterSize.W342,
            contentDescription = movie.title,
        )
    }
}

@Composable
private fun MovieThumbnailCard(
    movie: MovieModel,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(0.7f)
                .clickable { navigateMovieDetail(movie.id) }
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        ) {
            TMDBAsyncImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = movie.posterUrl,
                tmdbImageSize = PosterSize.W185,
                contentDescription = movie.title,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
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

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
        )
    }
}

private fun generateMovieSectionLabel(movieListType: MovieListType): Int =
    when (movieListType) {
        MovieListType.NOW_PLAYING -> R.string.feature_home_now_playing
        MovieListType.POPULAR -> R.string.feature_home_popular
        MovieListType.TOP_RATED -> R.string.feature_home_top_rated
        MovieListType.UPCOMING -> R.string.feature_home_upcoming
    }

@Preview(showBackground = true)
@Composable
private fun MovieThumbnailCardPreview() {
    val movie = MovieModel(
        id = 9054,
        title = "test title1",
        posterUrl = "https://www.google.com/#q=vituperatoribus",
        voteAverage = 2.3
    )
    MovieFinderTheme {
        MovieThumbnailCard(
            movie = movie,
            onBookmarkMovie = {},
            navigateMovieDetail = {}
        )
    }

}