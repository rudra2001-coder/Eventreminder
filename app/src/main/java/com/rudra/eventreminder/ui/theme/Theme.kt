package com.rudra.eventreminder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import java.time.LocalDate

enum class Theme {
    DEFAULT,
    GRADIENT,
    GLASSMORPHIC,
    AMOLED,
    FESTIVAL
}

private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = LightEmeraldGreen,
    background = DarkBlueGray,
    surface = DarkSlateGray,
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = LightGray,
    onSurface = LightGray,
    onError = White
)

private val LightColorScheme = lightColorScheme(
    primary = BrightBlue,
    secondary = EmeraldGreen,
    background = OffWhiteGray,
    surface = White,
    error = SoftRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = VeryDarkGray,
    onSurface = VeryDarkGray,
    onError = White
)

@Composable
fun EventReminderTheme(
    theme: Theme = Theme.DEFAULT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Enabled dynamic color
    content: @Composable () -> Unit
) {
    when (theme) {
        Theme.DEFAULT -> {
            DefaultTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
        }
        Theme.GRADIENT -> {
            GradientTheme(darkTheme = darkTheme, content = content)
        }
        Theme.GLASSMORPHIC -> {
            GlassmorphicTheme(darkTheme = darkTheme, content = content)
        }
        Theme.AMOLED -> {
            AmoledTheme(darkTheme = darkTheme, content = content)
        }
        Theme.FESTIVAL -> {
            FestivalTheme(darkTheme = darkTheme, content = content)
        }
    }
}

@Composable
fun DefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Enabled dynamic color
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun GradientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GradientDarkColorScheme else GradientLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun GlassmorphicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GlassmorphicDarkColorScheme else GlassmorphicLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun AmoledTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AmoledDarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun FestivalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val festival = getFestivalForDate(LocalDate.now())
    val colorScheme = when (festival) {
        Festival.CHRISTMAS -> ChristmasColorScheme
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
