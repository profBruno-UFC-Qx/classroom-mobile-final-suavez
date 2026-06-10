package com.example.projectstudy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

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

data class LumioExtraColors(
    val floatingBar: Color,
    val floatingBarSelected: Color,
    val floatingBarBorder: Color,
    val floatingBarInactive: Color
)

private val LightLumioExtraColors = LumioExtraColors(
    floatingBar = FloatingBarLight,
    floatingBarSelected = FloatingBarSelectedLight,
    floatingBarBorder = FloatingBarBorderLight,
    floatingBarInactive = FloatingBarInactiveLight
)

private val DarkLumioExtraColors = LumioExtraColors(
    floatingBar = FloatingBarDark,
    floatingBarSelected = FloatingBarSelectedDark,
    floatingBarBorder = FloatingBarBorderDark,
    floatingBarInactive = FloatingBarInactiveDark
)

private val LocalLumioExtraColors = staticCompositionLocalOf {
    LightLumioExtraColors
}

object LumioTheme {
    val colors: LumioExtraColors
        @Composable
        @ReadOnlyComposable
        get() = LocalLumioExtraColors.current
}

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

    val extraColors = if (darkTheme) {
        DarkLumioExtraColors
    } else {
        LightLumioExtraColors
    }

    CompositionLocalProvider(
        LocalLumioExtraColors provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}