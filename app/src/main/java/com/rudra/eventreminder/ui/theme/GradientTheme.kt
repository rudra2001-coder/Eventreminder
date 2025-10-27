package com.rudra.eventreminder.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val GradientStart = Color(0xFF6A11CB)
val GradientEnd = Color(0xFF2575FC)

val GradientLightColorScheme = lightColorScheme(
    primary = GradientStart,
    secondary = GradientEnd,
    background = OffWhiteGray,
    surface = White,
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = VeryDarkGray,
    onSurface = VeryDarkGray,
    onError = White
)

val GradientDarkColorScheme = darkColorScheme(
    primary = GradientStart,
    secondary = GradientEnd,
    background = DarkBlueGray,
    surface = DarkSlateGray,
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = LightGray,
    onSurface = LightGray,
    onError = White
)
