package com.example.projectstudy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenDark,
    onPrimary = GreenDarkFill,

    primaryContainer = GreenDarkFill,
    onPrimaryContainer = TextPrimaryDark,

    secondary = GreenAccentDark,
    onSecondary = GreenDarkFill,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextPrimaryDark,

    surfaceVariant = SurfaceDark2,
    onSurfaceVariant = TextMutedDark,

    outline = BorderDark,
    outlineVariant = BorderDark,

    error = ErrorDark,
    onError = BackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = BackgroundLight,

    primaryContainer = GreenLightFill,
    onPrimaryContainer = GreenDarkText,

    secondary = PrimaryGreenLightStrong,
    onSecondary = BackgroundLight,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = SurfaceLight,
    onSurface = TextPrimaryLight,

    surfaceVariant = GreenLightFill,
    onSurfaceVariant = TextMutedLight,

    outline = BorderLight,
    outlineVariant = BorderLight,

    error = ErrorLight,
    onError = BackgroundLight
)

@Composable
fun ProjectStudyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}