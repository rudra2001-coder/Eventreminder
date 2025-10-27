package com.rudra.eventreminder.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val GlassmorphicLightColorScheme = lightColorScheme(
    primary = BrightBlue,
    secondary = EmeraldGreen,
    background = OffWhiteGray.copy(alpha = 0.5f),
    surface = White.copy(alpha = 0.3f),
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = VeryDarkGray,
    onSurface = VeryDarkGray,
    onError = White
)

val GlassmorphicDarkColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = LightEmeraldGreen,
    background = DarkBlueGray.copy(alpha = 0.5f),
    surface = DarkSlateGray.copy(alpha = 0.3f),
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = LightGray,
    onSurface = LightGray,
    onError = White
)
