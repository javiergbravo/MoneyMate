package com.jgbravo.moneymate.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

// https://developer.android.com/jetpack/compose/designsystems/material2-material3#m3_1
@Composable
fun MoneyMateTheme(
    useDarkTheme: Boolean = false, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!useDarkTheme) LightThemeColors else DarkThemeColors
    val dimensions = when (LocalConfiguration.current.screenWidthDp) {
        in 0..TABLET_SIZE -> DefaultDimensions
        else -> TabletDimension
    }

    ProvideDimens(dimensions = dimensions) {
        ProvideTypography(typography = MoneyMayteTypography) {
            MaterialTheme(
                colorScheme = colorScheme,
                shapes = MoneyMateShapes,
                typography = MoneyMayteTypography,
                content = content,
            )
        }
    }
}

object AppTheme {

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}