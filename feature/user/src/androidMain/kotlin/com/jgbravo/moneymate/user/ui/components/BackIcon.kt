package com.jgbravo.moneymate.user.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme

@Composable
fun BackIcon(
    navigateBack: () -> Unit
) {
    IconButton(
        onClick = navigateBack
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun BackIconPreview() {
    MoneyMateTheme {
        BackIcon(
            navigateBack = {}
        )
    }
}