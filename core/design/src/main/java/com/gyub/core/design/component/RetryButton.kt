package com.gyub.core.design.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

/**
 * 재시도 버튼
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun RetryButton(
    modifier: Modifier = Modifier,
    retryMessage: Int,
    onRetry: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(retryMessage),
            modifier = Modifier
                .align(Alignment.Center)
                .clickable(onClick = onRetry)
        )
    }
}