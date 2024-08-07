package com.gyub.moviefinder.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Color.White,
    primaryContainer = LightBlue,
    onPrimaryContainer = Color.Black,
    secondary = Teal,
    onSecondary = Color.Black,
    secondaryContainer = LightTeal,
    onSecondaryContainer = Color.Black,
    tertiary = Amber,
    onTertiary = Color.Black,
    tertiaryContainer = LightAmber,
    onTertiaryContainer = Color.Black,
    error = Red,
    onError = Color.White,
    errorContainer = LightRed,
    onErrorContainer = Color.Black,
    background = LightGray200,
    onBackground = Color.Black,
    surface = LightGray100,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE1E1E1),
    onSurfaceVariant = Color.Black,
    outline = Outline,
    inverseOnSurface = LightInverseOnSurface,
    inverseSurface = DarkInverseOnSurface,
    inversePrimary = LightInversePrimary,
    surfaceTint = LightSurfaceTint
)

val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    onPrimary = Color.Black,
    primaryContainer = Blue,
    onPrimaryContainer = Color.White,
    secondary = DarkTeal,
    onSecondary = Color.Black,
    secondaryContainer = Teal,
    onSecondaryContainer = Color.White,
    tertiary = DarkAmber,
    onTertiary = Color.Black,
    tertiaryContainer = Amber,
    onTertiaryContainer = Color.Black,
    error = DarkRed,
    onError = Color.Black,
    errorContainer = Red,
    onErrorContainer = Color.White,
    background = LightBlack500,
    onBackground = Color.White,
    surface = LightBlack500,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color.White,
    outline = Outline,
    inverseOnSurface = DarkInverseOnSurface,
    inverseSurface = LightInverseOnSurface,
    inversePrimary = DarkInversePrimary,
    surfaceTint = DarkSurfaceTint
)

@Composable
fun MovieFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

object MovieFinderTheme {
    val typography: MovieFinderTypography
        @Composable
        get() = LocalTypography.current
}