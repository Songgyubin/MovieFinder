package com.gyub.feature.bookmark

import android.content.res.Configuration
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
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

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
        BpkText(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.feature_bookmark_bookmark_title),
            style = BpkTheme.typography.heading4,
            color = BpkTheme.colors.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookmarkTopAppBarPreview() {
    BpkTheme {
        BookmarkTopAppBar()
    }
}