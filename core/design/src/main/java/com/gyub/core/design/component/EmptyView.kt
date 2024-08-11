package com.gyub.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * Empty View
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    emptyText: Int,
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(emptyText)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    EmptyView(emptyText = android.R.string.unknownName)
}