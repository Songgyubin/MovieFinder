package com.gyub.feature.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gyub.core.common.extensions.formatToSingleDecimal
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.theme.Amber
import com.gyub.core.design.theme.DarkGray100
import com.gyub.core.design.theme.MovieFinderTheme
import com.gyub.core.design.util.size.PosterSize
import com.gyub.feature.detail.model.MovieStatus.Companion.getMovieStatusByOriginalName

/**
 * 영화 상세 Top Bar
 *
 * @author   Gyub
 * @created  2024/08/21
 */
@Composable
fun MovieDetailTopAppBar(
    modifier: Modifier = Modifier,
    posterUrl: String,
    isBookmarked: Boolean,
    voteAverage: Double,
    voteCount: Int,
    status: String,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .systemBarsPadding()
            .height(405.dp)
    ) {
        TMDBAsyncImage(
            imageUrl = posterUrl,
            tmdbImageSize = PosterSize.W342,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(bottomStart = 48.dp)),
            contentDescription = "movie backdrop"
        )

        IconButton(
            modifier = Modifier
                .padding(8.dp),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "icon back"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
        ) {
            Spacer(modifier = Modifier.width(32.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 15.dp,
                shape = RoundedCornerShape(topStart = 48.dp, bottomStart = 48.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 17.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MovieBookmark(isBookmarked)
                    MovieVoteAverage(
                        voteAverage = voteAverage,
                        voteCount = voteCount
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = MovieFinderTheme.typography.bodyLargeB,
                        text = stringResource(id = getMovieStatusByOriginalName(status).displayName)
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.MovieBookmark(isBookmarked: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (isBookmarked) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            }, contentDescription = "bookmark"
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            style = MovieFinderTheme.typography.bodyLargeB,
            text = stringResource(R.string.feature_detail_bookmark)
        )
    }

}

@Composable
fun MovieVoteAverage(
    voteAverage: Double,
    voteCount: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Amber,
            contentDescription = ""
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MovieFinderTheme.typography.bodyLargeB,
                color = Color.Black,
                text = voteAverage.formatToSingleDecimal()
            )
            Text(
                style = MovieFinderTheme.typography.bodyMediumR,
                color = DarkGray100,
                text = stringResource(R.string.feature_detail_vote_average, voteAverage)
            )
        }

        Text(
            modifier = Modifier.padding(top = 2.dp),
            style = MovieFinderTheme.typography.bodyMediumR,
            color = DarkGray100,
            text = "$voteCount",
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailTopAppBarPreview() {
    MovieFinderTheme {
        MovieDetailTopAppBar(
            posterUrl = "",
            voteAverage = 8.821,
            voteCount = 1000,
            isBookmarked = false,
            status = "개봉",
            onBackClick = {}
        )
    }
}