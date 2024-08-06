package com.gyub.moviefinder.design.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.gyub.core.common.tmdb.size.TmdbImageSize
import com.gyub.moviefinder.R

/**
 * TMDB Poster AsyncImage
 * TMDB 특성 상 응답으로 오는 poster_path(파일명)에 BaseUrl을 붙여서
 * 이미지를 받아와야 함
 *
 * https://image.tmdb.org/t/p/w200(이미지 크기)/파일명
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun PosterAsyncImage(
    imageUrl: String,
    tmdbImageSize: TmdbImageSize,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Int = R.drawable.loading_img,
    error: Int = R.drawable.ic_broken_image,
) {
    val posterUrl = BASE_POSTER_URL + tmdbImageSize.toString() + imageUrl

    DefaultAsyncImage(
        imageUrl = posterUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeholder,
        error = error
    )
}

private val BASE_POSTER_URL: String by lazy { "https://image.tmdb.org/t/p/" }