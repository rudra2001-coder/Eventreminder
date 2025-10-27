package com.rudra.eventreminder.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.Month

enum class Festival {
    NONE,
    CHRISTMAS
}

fun getFestivalForDate(date: LocalDate): Festival {
    return when {
        date.month == Month.DECEMBER && date.dayOfMonth == 25 -> Festival.CHRISTMAS
        else -> Festival.NONE
    }
}

val ChristmasColorScheme = lightColorScheme(
    primary = Color(0xFFC62828), // Red
    secondary = Color(0xFF2E7D32), // Green
    background = OffWhiteGray,
    surface = White,
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = VeryDarkGray,
    onSurface = VeryDarkGray,
    onError = White
)
