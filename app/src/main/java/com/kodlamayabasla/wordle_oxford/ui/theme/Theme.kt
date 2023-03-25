package com.kodlamayabasla.wordle_oxford.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
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

)

var incorrectBackgroundScheme by mutableStateOf(incorrectBackgroundDark)
var keyboardScheme by mutableStateOf(keyboardDark)
var onKeyboardScheme by mutableStateOf(onKeyboardDark)
var onCorrectBackgroundScheme by mutableStateOf(onCorrectBackgroundDark)
var onWrongPositionBackgroundScheme by mutableStateOf(onWrongPositionBackgroundDark)
var onIncorrectBackgroundScheme by mutableStateOf(onIncorrectBackgroundDark)
var borderScheme by mutableStateOf(borderDark)


val ColorScheme.correctBackground @Composable get() = Color(0xFF6CA965)
val ColorScheme.wrongPositionBackground @Composable get() = Color(0xFFC8B653)
var ColorScheme.incorrectBackground: Color get() = incorrectBackgroundScheme
    set(value) { incorrectBackgroundScheme = value}
val ColorScheme.enteringBackground @Composable get() = MaterialTheme.colorScheme.background
var ColorScheme.keyboard: Color get() = keyboardScheme
    set(value) {keyboardScheme = value}
val ColorScheme.keyboardDisabled @Composable get() = Color(0xFF4E4E50)
var ColorScheme.onKeyboard: Color get() = onKeyboardScheme
    set(value) {onKeyboardScheme = value}
var ColorScheme.onCorrectBackground: Color get() = onCorrectBackgroundScheme
    set(value) {onCorrectBackgroundScheme = value}
var ColorScheme.onWrongPositionBackground: Color get() = onWrongPositionBackgroundScheme
    set(value) {onWrongPositionBackgroundScheme = value}
var ColorScheme.onIncorrectBackground: Color get() = onIncorrectBackgroundScheme
    set(value) {onIncorrectBackgroundScheme = value}
var ColorScheme.border: Color get() =borderScheme
    set(value) {borderScheme = value}

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

    if(darkTheme) {
        colorScheme.incorrectBackground = incorrectBackgroundDark
        colorScheme.keyboard = keyboardDark
        colorScheme.onKeyboard = onKeyboardDark
        colorScheme.onCorrectBackground = onCorrectBackgroundDark
        colorScheme.onWrongPositionBackground = onWrongPositionBackgroundDark
        colorScheme.onIncorrectBackground = onIncorrectBackgroundDark
        colorScheme.border = borderDark
    } else {
        colorScheme.incorrectBackground = incorrectBackgroundLight
        colorScheme.keyboard = keyboardLight
        colorScheme.onKeyboard = onKeyboardLight
        colorScheme.onCorrectBackground = onCorrectBackgroundLight
        colorScheme.onWrongPositionBackground = onWrongPositionBackgroundLight
        colorScheme.onIncorrectBackground = onIncorrectBackgroundLight
        colorScheme.border = borderLight
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