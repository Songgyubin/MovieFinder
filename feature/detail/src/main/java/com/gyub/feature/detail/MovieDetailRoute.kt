package com.gyub.feature.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.core.design.component.LoadingIndicator
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.theme.MovieFinderTheme
import com.gyub.core.design.util.size.ProfileSize
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.feature.detail.model.MovieDetailUiState

/**
 * 영화 상세 화면
 *
 * @author   Gyub
 * @created  2024/08/21
 */
@Composable
fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieId: Int,
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val movieDetailUiState by viewModel.movieDetailUiState.collectAsStateWithLifecycle()

    MovieDetailContent(
        modifier = modifier,
        movieDetailUiState = movieDetailUiState,
        onBackClick = onBackClick
    )

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetail(movieId)
    }
}

@Composable
fun MovieDetailContent(
    modifier: Modifier = Modifier,
    movieDetailUiState: MovieDetailUiState,
    onBackClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (movieDetailUiState) {
            MovieDetailUiState.Error -> {}
            MovieDetailUiState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
            is MovieDetailUiState.Success -> MovieDetailScreen(
                movieDetailUiState = movieDetailUiState,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun BoxScope.MovieDetailScreen(
    movieDetailUiState: MovieDetailUiState.Success,
    onBackClick: () -> Unit,
) {
    val movieDetail = movieDetailUiState.movieDetail
    val movieCredits = movieDetailUiState.movieCredits

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MovieDetailTopAppBar(
            modifier = Modifier.background(Color.Transparent),
            posterUrl = movieDetail.posterUrl,
            isBookmarked = movieDetail.isBookmarked,
            voteAverage = movieDetail.voteAverage,
            status = movieDetail.status,
            voteCount = movieDetail.voteCount,
            onBackClick = onBackClick
        )

        MovieInfoSection(
            title = movieDetail.title,
            releaseDate = movieDetail.releaseDate,
            runtime = movieDetail.runtime,
            genres = movieDetail.genres,
            overview = movieDetail.overview
        )

        MovieCastSection(
            director = movieCredits.director,
            casts = movieCredits.casts
        )
    }
}

@Composable
fun MovieInfoSection(
    title: String,
    releaseDate: String,
    runtime: Int,
    genres: List<MovieDetailModel.GenreModel>,
    overview: String,
) {
    val localContext = LocalContext.current

    Column(
        modifier = Modifier.padding(top = 48.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 32.dp),
            style = MovieFinderTheme.typography.headlineMediumB,
            text = title,
        )
        Row(
            modifier = Modifier.padding(start = 32.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MovieFinderTheme.typography.titleMediumR,
                text = releaseDate,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(28.dp))
            Text(
                style = MovieFinderTheme.typography.titleMediumR,
                text = runtime.toHourMinuteString(localContext),
                color = Color.DarkGray
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
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        text = genre.name
                    )
                }
            }
        }

        MovieOverview(
            overview = overview
        )
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 48.dp),
            text = stringResource(R.string.feature_detail_overview),
            style = MovieFinderTheme.typography.titleLargeB
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = overview,
            color = Color.DarkGray,
            style = MovieFinderTheme.typography.bodyMediumR
        )
    }
}

@Composable
fun MovieCastSection(
    director: MovieCreditsModel.CrewMemberModel,
    casts: List<MovieCreditsModel.CastMemberModel>,
) {
    Column(
        modifier = Modifier.padding(top = 48.dp, bottom = 30.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 32.dp),
            style = MovieFinderTheme.typography.titleLargeB,
            text = stringResource(R.string.feature_detail_director_cast)
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            item {
                ProfileImage(
                    profilePath = director.profilePath,
                    name = director.name,
                    job = director.job
                )
            }
            items(casts) {
                Spacer(modifier = Modifier.width(28.dp))
                ProfileImage(
                    profilePath = it.profilePath,
                    name = it.name,
                    job = it.character
                )
            }
        }
    }
}

@Composable
private fun ProfileImage(
    profilePath: String,
    name: String,
    job: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TMDBAsyncImage(
            imageUrl = profilePath,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            tmdbImageSize = ProfileSize.W185,
            contentDescription = ""
        )
        Text(
            text = name,
        )
        Text(
            text = job,
            color = Color.DarkGray
        )
    }
}

/**
 * Int의 값을 시간으로 반환
 * 128 -> 2시간 8분
 *
 * @return
 */
fun Int.toHourMinuteString(context: Context): String {
    val hours = this / 60
    val minutes = this % 60
    return context.getString(R.string.feature_detail_hour_min, hours, minutes)
}

@Preview(showBackground = true)
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
        movieCredits = MovieCreditsModel(
            director = MovieCreditsModel.CrewMemberModel(
                id = 2404,
                job = "감독",
                name = "Jo Wall",
                profilePath = "ultrices"
            ),
            casts = listOf(
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
            )
        )
    )
    MovieFinderTheme {
        MovieDetailContent(movieDetailUiState = movieDetailUiState, onBackClick = {})
    }
}

