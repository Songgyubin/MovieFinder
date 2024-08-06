package com.gyub.moviefinder.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gyub.moviefinder.R
import com.gyub.moviefinder.design.theme.MovieFinderTheme

/**
 * 북마크 상단 바
 *
 * @author   Gyub
 * @created  2024/07/22
 */
@Composable
fun BookmarkTopAppBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.bookmark),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkTopAppBarPreview() {
    MovieFinderTheme {
        BookmarkTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkTopAppBarDeleteModePreview() {
    MovieFinderTheme {
        BookmarkTopAppBar()
    }
}