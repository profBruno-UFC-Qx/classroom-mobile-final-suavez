package com.example.projectstudy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(

    primary = PrimaryGreenDark,

    background = BackgroundDark,
    surface = SurfaceDark,

    onPrimary = TextPrimaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,

    secondary = GreenAccentDark,

    error = ErrorDark
)

private val LightColorScheme = lightColorScheme(

    primary = PrimaryGreenLight,

    background = BackgroundLight,
    surface = SurfaceLight,

    onPrimary = BackgroundLight,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,

    secondary = GreenDarkText,

    error = ErrorLight
)

@Composable
fun ProjectStudyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
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