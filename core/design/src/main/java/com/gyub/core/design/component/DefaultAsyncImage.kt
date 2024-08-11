package com.gyub.core.design.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gyub.core.design.R

/**
 * 기본 Async 이미지
 *
 * @author   Gyub
 * @created  2024/07/22
 */
// TODO: 캐싱 정책 및 리사이징
@Composable
fun DefaultAsyncImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Int = R.drawable.loading_img,
    error: Int = R.drawable.ic_broken_image,
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        error = painterResource(id = error),
        placeholder = painterResource(id = placeholder),
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier
    )
}