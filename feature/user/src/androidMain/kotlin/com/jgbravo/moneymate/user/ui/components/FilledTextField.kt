package com.jgbravo.moneymate.user.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.jgbravo.moneymate.core.ui.theme.RedStop
import com.jgbravo.moneymate.user.R

@Composable
fun FilledTextField(
    @StringRes label: Int,
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    hideText: Boolean = false,
    @StringRes error: Int? = null,
    isSingleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var textIsVisible by remember { mutableStateOf(!hideText) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = stringResource(id = label)) },
        isError = error != null,
        singleLine = isSingleLine,
        supportingText = {
            if (error != null) {
                Text(
                    modifier = modifier,
                    text = stringResource(id = error),
                    color = RedStop
                )
            }
        },
        visualTransformation = if (textIsVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = if (hideText) {
            {
                val icon = if (textIsVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                IconButton(
                    onClick = {
                        textIsVisible = !textIsVisible
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        },
        keyboardOptions = if (hideText) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            keyboardOptions
        }
    )
}

@Preview
@Composable
fun SimpleFilledTextFieldSamplePreview() {
    FilledTextField(
        label = R.string.email,
        text = "Pérdida de grasa",
        onTextChange = {},
        error = R.string.invalid_email,
    )
}