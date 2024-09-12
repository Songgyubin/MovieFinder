package com.gyub.feature.home

import android.content.res.Configuration
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.util.size.PosterSize
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.model.MovieListType
import com.gyub.feature.home.model.MovieSectionData
import com.gyub.feature.home.model.SectionUiState
import com.gyub.feature.home.model.SectionsState
import kotlinx.collections.immutable.persistentListOf
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

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
    val sections by viewModel.sections.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceDefault)
            .systemBarsPadding()
            .padding(bottom = 56.dp)
    ) {
        HomeScreen(
            sectionsState = sections,
            onBookmarkMovie = viewModel::onBookmarkMovie,
            notifyErrorMessage = viewModel::notifyErrorMessage,
            navigateMovieDetail = navigateMovieDetail
        )
    }
}

@Composable
fun HomeScreen(
    sectionsState: SectionsState,
    notifyErrorMessage: (String) -> Unit,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    MovieContent(
        sectionsState = sectionsState,
        onBookmarkMovie = onBookmarkMovie,
        notifyErrorMessage = notifyErrorMessage,
        navigateMovieDetail = navigateMovieDetail
    )
}

@Composable
fun MovieContent(
    sectionsState: SectionsState,
    onBookmarkMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (String) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        orderedSections.forEach { movieListType ->
            val sectionUiState = sectionsState.sections[movieListType]
            sectionUiState?.let {
                item {
                    MovieSectionContent(
                        movieListType = movieListType,
                        sectionUiState = it,
                        onBookmarkMovie = onBookmarkMovie,
                        notifyErrorMessage = notifyErrorMessage,
                        navigateMovieDetail = navigateMovieDetail
                    )
                }
            }
        }
    }
}

@Composable
fun MovieSectionContent(
    movieListType: MovieListType,
    sectionUiState: SectionUiState,
    onBookmarkMovie: (MovieModel) -> Unit,
    notifyErrorMessage: (String) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
) {
    when (movieListType) {
        MovieListType.NOW_PLAYING -> {
            MovieViewPager(
                sectionUiState = sectionUiState,
                notifyErrorMessage = notifyErrorMessage
            )
        }

        else -> {
            MovieSection(
                sectionUiState = sectionUiState,
                onBookmarkMovie = onBookmarkMovie,
                notifyErrorMessage = notifyErrorMessage,
                navigateMovieDetail = navigateMovieDetail
            )
        }
    }
}

@Composable
fun MovieSection(
    sectionUiState: SectionUiState,
    onBookmarkMovie: (MovieModel) -> Unit,
    navigateMovieDetail: (Int) -> Unit,
    notifyErrorMessage: (String) -> Unit,
) {
    when (sectionUiState) {
        is SectionUiState.Error -> {
            notifyErrorMessage(sectionUiState.uiText.asString())
        }

        SectionUiState.Loading -> {
            LoadingIndicator()
        }

        is SectionUiState.Success -> {
            val (movies, movieListType) = sectionUiState.movieSectionData

            Column {
                BpkText(
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = BpkTheme.typography.heading4,
                    color = BpkTheme.colors.textPrimary,
                    textAlign = TextAlign.Center,
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
    }
}

@Composable
fun MovieViewPager(
    sectionUiState: SectionUiState,
    notifyErrorMessage: (String) -> Unit,
) {
    when (sectionUiState) {
        is SectionUiState.Error -> {
            notifyErrorMessage(sectionUiState.uiText.asString())
        }

        SectionUiState.Loading -> {
            LoadingIndicator()
        }

        is SectionUiState.Success -> {
            val movies = sectionUiState.movieSectionData.movies
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

        BpkText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = movie.title,
            style = BpkTheme.typography.heading5.copy(
                fontWeight = FontWeight.Medium
            ),
            color = BpkTheme.colors.textPrimary,
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

/**
 * 섹션 UI 순서
 */
private val orderedSections = persistentListOf(
    MovieListType.NOW_PLAYING,
    MovieListType.POPULAR,
    MovieListType.TOP_RATED,
    MovieListType.UPCOMING,
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun MovieThumbnailCardPreview() {
    val movie = MovieModel(
        id = 9054,
        title = "test title1",
        posterUrl = "https://www.google.com/#q=vituperatoribus",
        voteAverage = 2.3
    )
    BpkTheme {
        MovieThumbnailCard(
            movie = movie,
            onBookmarkMovie = {},
            navigateMovieDetail = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun MovieSectionPreview() {
    val sectionUiState = SectionUiState.Success(
        movieSectionData = MovieSectionData(
            movies = persistentListOf(
                MovieModel(
                    id = 9054,
                    title = "test title1",
                    posterUrl = "https://www.google.com/#q=vituperatoribus",
                    voteAverage = 2.3,
                )
            ),
            movieListType = MovieListType.NOW_PLAYING
        )
    )
    BpkTheme {
        MovieSection(
            sectionUiState = sectionUiState,
            onBookmarkMovie = {},
            navigateMovieDetail = {},
            notifyErrorMessage = { }
        )
    }
}