package com.gyub.moviefinder.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val SansSerifStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
)

val Typography = MovieFinderTypography(
    displayLargeB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),
    displayMediumB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp
    ),
    displaySmallB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineLargeB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMediumB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    headlineSmallB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLargeB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMediumB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMediumR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleSmallM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLargeR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMediumR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmallR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLargeM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMediumM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmallM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)

@Immutable
data class MovieFinderTypography(
    val displayLargeB: TextStyle,
    val displayMediumB: TextStyle,
    val displaySmallB: TextStyle,

    val headlineLargeB: TextStyle,
    val headlineMediumB: TextStyle,
    val headlineSmallB: TextStyle,

    val titleLargeB: TextStyle,
    val titleMediumB: TextStyle,
    val titleMediumR: TextStyle,
    val titleSmallM: TextStyle,

    val bodyLargeR: TextStyle,
    val bodyMediumR: TextStyle,
    val bodySmallR: TextStyle,

    val labelLargeM: TextStyle,
    val labelMediumM: TextStyle,
    val labelSmallM: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    MovieFinderTypography(
        displayLargeB = SansSerifStyle,
        displayMediumB = SansSerifStyle,
        displaySmallB = SansSerifStyle,
        headlineLargeB = SansSerifStyle,
        headlineMediumB = SansSerifStyle,
        headlineSmallB = SansSerifStyle,
        titleLargeB = SansSerifStyle,
        titleMediumB = SansSerifStyle,
        titleMediumR = SansSerifStyle,
        titleSmallM = SansSerifStyle,
        bodyLargeR = SansSerifStyle,
        bodyMediumR = SansSerifStyle,
        bodySmallR = SansSerifStyle,
        labelLargeM = SansSerifStyle,
        labelMediumM = SansSerifStyle,
        labelSmallM = SansSerifStyle,
    )
}