package com.jgbravo.moneymate.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// https://developer.android.com/jetpack/compose/designsystems/material2-material3#m3_1
@Composable
fun MoneyMateTheme(
    useDarkTheme: Boolean = false, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!useDarkTheme) LightThemeColors else DarkThemeColors

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoneyMateShapes,
        typography = MoneyMayteTypography,
        content = content,
    )
}