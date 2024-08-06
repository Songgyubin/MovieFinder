package com.gyub.moviefinder.design.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
    background = LightBackground,
    onBackground = Color.Black,
    surface = LightSurface,
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
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
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
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MovieFinderTypography,
        content = content
    )
}