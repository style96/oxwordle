package com.kodlamayabasla.wordle_oxford.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    onBackground = onBackgroundDark,
    onPrimary = onPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    tertiary = tertiaryLight,
    onBackground = onBackgroundLight,
    onPrimary = onPrimaryLight,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
/*
val ColorScheme.correctBackground @Composable get() = Color(0xFF6CA965)
val ColorScheme.wrongPositionBackground @Composable get() = Color(0xFFC8B653)
val ColorScheme.incorrectBackground @Composable get() = Color(0xFF5B5B5B)
val ColorScheme.enteringBackground @Composable get() = MaterialTheme.colorScheme.background
val ColorScheme.keyboard @Composable get() = Color(0xFF787C7F)
val ColorScheme.keyboardDisabled @Composable get() = Color(0xFF4E4E50)
val ColorScheme.onKeyboard @Composable get() = Color(0xFFE7E7E7)
*/
val ColorScheme.correctBackground @Composable get() = Color(0xFF6CA965)
val ColorScheme.wrongPositionBackground @Composable get() = Color(0xFFC8B653)
val ColorScheme.incorrectBackground @Composable get() = if (isSystemInDarkTheme()) incorrectBackgroundDark else incorrectBackgroundLight
val ColorScheme.enteringBackground @Composable get() = MaterialTheme.colorScheme.background
val ColorScheme.keyboard @Composable get() = if (isSystemInDarkTheme()) keyboardDark else keyboardLight
val ColorScheme.keyboardDisabled @Composable get() = Color(0xFF4E4E50)
val ColorScheme.onKeyboard @Composable get() = if (isSystemInDarkTheme()) onKeyboardDark else onKeyboardLight
val ColorScheme.onCorrectBackground @Composable get() = if (isSystemInDarkTheme()) onCorrectBackgroundDark else onCorrectBackgroundLight
val ColorScheme.onWrongPositionBackground @Composable get() = if (isSystemInDarkTheme()) onWrongPositionBackgroundDark else onWrongPositionBackgroundLight
val ColorScheme.onIncorrectBackground @Composable get() = if (isSystemInDarkTheme()) onIncorrectBackgroundDark else onIncorrectBackgroundLight
val ColorScheme.border @Composable get() = if (isSystemInDarkTheme()) borderDark else borderLight

@Composable
fun OxWordleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}