package com.gyub.feature.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gyub.core.common.extensions.formatToSingleDecimal
import com.gyub.core.design.component.TMDBAsyncImage
import com.gyub.core.design.theme.Amber
import com.gyub.core.design.theme.MovieFinderTheme
import com.gyub.core.design.util.size.PosterSize
import com.gyub.feature.detail.model.MovieStatus.Companion.getMovieStatusByOriginalName
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.icon.findByName
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * 영화 상세 Top Bar
 *
 * @author   Gyub
 * @created  2024/08/21
 */
@Composable
fun MovieDetailTopAppBar(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    posterUrl: String,
    isBookmarked: Boolean,
    voteAverage: Double,
    voteCount: Int,
    releaseDate: String,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(BpkTheme.colors.surfaceDefault)
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
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            onClick = onBackClick
        ) {
            val icon = BpkIcon.findByName("native-android--back")
            icon?.let {
                BpkIcon(
                    icon = it,
                    contentDescription = null,
                    size = BpkIconSize.Large
                )
            }
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
                        .background(BpkTheme.colors.surfaceHighlight)
                        .padding(top = 17.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MovieBookmark(isBookmarked)
                    MovieVoteAverage(
                        voteAverage = voteAverage,
                        voteCount = voteCount
                    )
                    BpkText(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = BpkTheme.typography.heading5,
                        color = BpkTheme.colors.textPrimary,
                        text = stringResource(id = getMovieStatusByOriginalName(releaseDate).displayName)
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
            }, contentDescription = "bookmark",
            tint = BpkTheme.colors.textPrimary
        )
        BpkText(
            modifier = Modifier.padding(top = 4.dp),
            style = BpkTheme.typography.heading5,
            color = BpkTheme.colors.textPrimary,
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
            BpkText(
                style = BpkTheme.typography.heading5,
                color = BpkTheme.colors.textPrimary,
                text = voteAverage.formatToSingleDecimal()
            )
            BpkText(
                style = BpkTheme.typography.heading5.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = BpkTheme.colors.textPrimary,
                text = stringResource(R.string.feature_detail_vote_average, voteAverage)
            )
        }

        BpkText(
            modifier = Modifier.padding(top = 2.dp),
            style = BpkTheme.typography.heading5,
            color = BpkTheme.colors.textPrimary,
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
            isBookmarked = false,
            voteAverage = 8.821,
            voteCount = 1000,
            releaseDate = "2024-08-26",
            onBackClick = {},
            innerPadding = PaddingValues()
        )
    }
}