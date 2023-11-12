package com.jgbravo.moneymate.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal const val TABLET_SIZE = 360

data class Dimensions(
    val verySmall: Dp,
    val small: Dp,
    val medium: Dp,
    val normal: Dp,
    val large: Dp,
    val veryLarge: Dp,
    val minimumTouchTarget: Dp,
)

internal val DefaultDimensions = Dimensions(
    verySmall = 2.dp,
    small = 4.dp,
    medium = 8.dp,
    normal = 16.dp,
    large = 24.dp,
    veryLarge = 32.dp,
    minimumTouchTarget = 48.dp,
)

// Not tested yet
internal val TabletDimension = Dimensions(
    verySmall = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    normal = 24.dp,
    large = 32.dp,
    veryLarge = 48.dp,
    minimumTouchTarget = 64.dp,
)

internal val LocalAppDimens = staticCompositionLocalOf { DefaultDimensions }

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}