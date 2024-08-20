package com.gyub.core.design.theme

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
    displayLargeB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),
    displayMediumB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp
    ),
    displaySmallB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineLargeB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMediumB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    headlineSmallB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLargeB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMediumB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMediumR = SansSerifStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleSmallM = SansSerifStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLargeB = SansSerifStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLargeR = SansSerifStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMediumR = SansSerifStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmallR = SansSerifStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLargeM = SansSerifStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMediumM = SansSerifStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmallM = SansSerifStyle.copy(
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

    val bodyLargeB: TextStyle,
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
        bodyLargeB = SansSerifStyle,
        bodyLargeR = SansSerifStyle,
        bodyMediumR = SansSerifStyle,
        bodySmallR = SansSerifStyle,
        labelLargeM = SansSerifStyle,
        labelMediumM = SansSerifStyle,
        labelSmallM = SansSerifStyle,
    )
}