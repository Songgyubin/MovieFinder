package com.gyub.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

val OnlyDarkColorScheme = lightColorScheme(
    primary = Blue,
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
    CompositionLocalProvider(
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = OnlyDarkColorScheme,
            content = content,
        )
    }
}

object MovieFinderTheme {
    val typography: MovieFinderTypography
        @Composable
        get() = LocalTypography.current
}