package com.gyub.feature.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.util.size.PosterSize
import com.gyub.core.design.util.size.ProfileSize
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.ui.util.toHourMinuteString
import com.gyub.feature.detail.model.MovieDetailUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * 영화 상세 화면
 *
 * @author   Gyub
 * @created  2024/08/21
 */
@Composable
fun MovieDetailRoute(
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val movieDetailUiState by viewModel.movieDetailUiState.collectAsStateWithLifecycle()

    MovieDetailContent(
        innerPadding = innerPadding,
        movieDetailUiState = movieDetailUiState,
        notifyErrorMessage = viewModel::notifyErrorMessage,
        onBackClick = onBackClick
    )
}

@Composable
fun MovieDetailContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    movieDetailUiState: MovieDetailUiState,
    notifyErrorMessage: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceDefault)
            .padding(bottom = 32.dp)
    ) {
        when (movieDetailUiState) {
            is MovieDetailUiState.Error -> {
                notifyErrorMessage(movieDetailUiState.uiText.asString())
            }

            MovieDetailUiState.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }

            is MovieDetailUiState.Success -> {
                MovieDetailScreen(
                    innerPadding = innerPadding,
                    movieDetailUiState = movieDetailUiState,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
fun BoxScope.MovieDetailScreen(
    innerPadding: PaddingValues,
    movieDetailUiState: MovieDetailUiState.Success,
    onBackClick: () -> Unit,
) {
    val movieDetail = movieDetailUiState.movieDetail
    val director = movieDetailUiState.director
    val cast = movieDetailUiState.cast
    val similarMovies = movieDetailUiState.similarMovies
    val recommendationMovies = movieDetailUiState.recommendationMovies

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceDefault)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        MovieDetailTopAppBar(
            innerPadding = innerPadding,
            posterUrl = movieDetail.posterUrl,
            isBookmarked = movieDetail.isBookmarked,
            voteAverage = movieDetail.voteAverage,
            releaseDate = movieDetail.releaseDate,
            voteCount = movieDetail.voteCount,
            onBackClick = onBackClick
        )

        MovieInfoSection(
            title = movieDetail.title,
            releaseDate = movieDetail.releaseDate,
            runtime = movieDetail.runtime,
            genres = movieDetail.genres.toPersistentList(),
        )

        MovieOverview(
            overview = movieDetail.overview
        )

        MovieCastSection(
            director = director,
            casts = cast
        )

        MovieSimilarMoviesSection(
            movies = similarMovies
        )

        MovieRecommendationMoviesSection(
            movies = recommendationMovies
        )
    }
    Spacer(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .height(32.dp)
    )
}

@Composable
fun MovieInfoSection(
    title: String,
    releaseDate: String,
    runtime: Int,
    genres: PersistentList<MovieDetailModel.GenreModel>,
) {
    val localContext = LocalContext.current

    Column {
        BpkText(
            modifier = Modifier.padding(start = 32.dp),
            style = BpkTheme.typography.heading3,
            color = BpkTheme.colors.textPrimary,
            text = title,
        )

        Row(
            modifier = Modifier.padding(start = 32.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BpkText(
                style = BpkTheme.typography.heading5,
                color = BpkTheme.colors.textPrimary,
                text = releaseDate,
            )
            Spacer(modifier = Modifier.width(28.dp))
            BpkText(
                style = BpkTheme.typography.heading5,
                color = BpkTheme.colors.textPrimary,
                text = runtime.toHourMinuteString(localContext),
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 20.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            itemsIndexed(genres) { index, genre ->
                if (index != 0) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(40.dp))
                ) {
                    BpkText(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = BpkTheme.typography.heading5,
                        color = BpkTheme.colors.textPrimary,
                        text = genre.name
                    )
                }
            }
        }
    }
}

@Composable
fun MovieOverview(overview: String) {
    if (overview.isEmpty()) {
        Label(
            modifier = Modifier.padding(
                start = 32.dp,
                end = 32.dp,
            ),
            textRes = R.string.feature_detail_no_overview
        )
    } else {
        Column(
            modifier = Modifier.padding(
                start = 32.dp,
                end = 32.dp,
            )
        ) {
            Label(
                textRes = R.string.feature_detail_overview
            )
            BpkText(
                style = BpkTheme.typography.bodyDefault,
                color = BpkTheme.colors.textSecondary,
                text = overview,
            )
        }
    }
}

@Composable
fun MovieCastSection(
    director: MovieCreditsModel.CrewMemberModel,
    casts: PersistentList<MovieCreditsModel.CastMemberModel>,
) {
    Column {
        Label(
            modifier = Modifier.padding(start = 32.dp),
            textRes = R.string.feature_detail_director_cast
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            item {
                ProfileItem(
                    modifier = Modifier.width(80.dp),
                    profilePath = director.profilePath,
                    name = director.name,
                    job = director.job
                )
            }
            items(casts) {
                Spacer(modifier = Modifier.width(28.dp))
                ProfileItem(
                    modifier = Modifier.width(80.dp),
                    profilePath = it.profilePath,
                    name = it.name,
                    job = it.character
                )
            }
        }
    }
}

@Composable
fun MovieSimilarMoviesSection(movies: PersistentList<MovieModel>) {
    if (movies.isEmpty()) return

    Column {
        Label(
            modifier = Modifier.padding(start = 32.dp),
            textRes = R.string.feature_detail_similar_movie
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            itemsIndexed(movies, key = { _, movie -> movie.id }) { index, movie ->
                if (index != 0 && index != movies.lastIndex) {
                    Spacer(modifier = Modifier.width(28.dp))
                }

                MovieSummaryCard(
                    modifier = Modifier.width(120.dp),
                    posterPath = movie.posterUrl,
                    title = movie.title,
                    isBookmarked = false
                )
            }
        }
    }
}

@Composable
fun MovieRecommendationMoviesSection(movies: PersistentList<MovieModel>) {
    if (movies.isEmpty()) return

    Column {
        Label(
            modifier = Modifier.padding(start = 32.dp),
            textRes = R.string.feature_detail_recommendation_movies
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            itemsIndexed(movies, key = { _, movie -> movie.id }) { index, movie ->
                if (index != 0 && index != movies.lastIndex) {
                    Spacer(modifier = Modifier.width(28.dp))
                }

                MovieSummaryCard(
                    modifier = Modifier.width(120.dp),
                    posterPath = movie.posterUrl,
                    title = movie.title,
                    isBookmarked = false
                )
            }
        }
    }
}

@Composable
fun MovieSummaryCard(
    modifier: Modifier = Modifier,
    posterPath: String,
    title: String,
    isBookmarked: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.width(150.dp)
        ) {
            TMDBAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f),
                imageUrl = posterPath,
                tmdbImageSize = PosterSize.W185,
                contentDescription = ""
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                onClick = {}
            ) {
                Icon(
                    imageVector = if (isBookmarked) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = ""
                )
            }
        }
        BpkText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            textAlign = TextAlign.Center,
            style = BpkTheme.typography.heading5,
            color = BpkTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = title
        )
    }
}

@Composable
private fun ProfileItem(
    modifier: Modifier = Modifier,
    profilePath: String,
    name: String,
    job: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TMDBAsyncImage(
            imageUrl = profilePath,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape),
            tmdbImageSize = ProfileSize.W185,
        )
        BpkText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = BpkTheme.typography.heading5,
            color = BpkTheme.colors.textPrimary,
            text = name
        )

        BpkText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = BpkTheme.typography.footnote,
            color = BpkTheme.colors.textPrimary,
            text = job,
        )
    }
}

@Composable
private fun Label(
    modifier: Modifier = Modifier,
    textRes: Int,
) {
    BpkText(
        modifier = modifier.padding(bottom = 16.dp),
        style = BpkTheme.typography.heading4,
        color = BpkTheme.colors.textPrimary,
        text = stringResource(textRes)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailContentPreview() {
    val genres = listOf(
        MovieDetailModel.GenreModel(
            id = 28,
            name = "액션"
        ),
        MovieDetailModel.GenreModel(
            id = 35,
            name = "코미디"
        ),
        MovieDetailModel.GenreModel(
            id = 878,
            name = "SF"
        )
    )
    val movieDetailUiState = MovieDetailUiState.Success(
        movieDetail = MovieDetailModel(
            title = "데드풀과 울버린",
            voteAverage = 8.821,
            isBookmarked = false,
            runtime = 128,
            voteCount = 2000,
            releaseDate = "2024-07-24",
            genres = genres,
            status = "상영 중",
            overview = "히어로 생활에서 은퇴한 후, 평범한 중고차 딜러로 살아가던 ‘데드풀’이 예상치 못한 거대한 위기를 맞아 모든 면에서 상극인 ‘울버린’을 찾아가게 되며 펼쳐지는 도파민 폭발 액션 블록버스터."
        ),
        director = MovieCreditsModel.CrewMemberModel(
            id = 2404,
            job = "감독",
            name = "Jo Wall",
            profilePath = "ultrices"
        ),
        cast = persistentListOf(
            MovieCreditsModel.CastMemberModel(
                id = 2404,
                name = "라이언 레이놀즈",
                character = "데드풀",
                profilePath = ""
            ),
            MovieCreditsModel.CastMemberModel(
                id = 2404,
                name = "휴 잭맨",
                character = "울버린",
                profilePath = ""
            ),
            MovieCreditsModel.CastMemberModel(
                id = 2404,
                name = "엠마 코린",
                character = "카산드라",
                profilePath = ""
            )
        ),
        similarMovies = persistentListOf(
            MovieModel(
                id = 2249,
                title = "test title 1test title 1test title 1test title 1",
            ),
            MovieModel(
                id = 2250,
                title = "test title 2",
            ),
        ),
        recommendationMovies = persistentListOf(
            MovieModel(
                id = 2249,
                title = "test title 1",
            ),
            MovieModel(
                id = 2250,
                title = "test title 2",
            )
        )
    )
    BpkTheme {
        MovieDetailContent(
            movieDetailUiState = movieDetailUiState,
            notifyErrorMessage = {},
            onBackClick = {},
            innerPadding = PaddingValues()
        )
    }
}

