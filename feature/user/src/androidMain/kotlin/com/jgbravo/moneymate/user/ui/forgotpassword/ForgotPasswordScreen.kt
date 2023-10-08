package com.jgbravo.moneymate.user.ui.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgbravo.moneymate.user.R
import com.jgbravo.moneymate.user.ui.components.BackIcon
import com.jgbravo.moneymate.user.ui.components.FilledTextField
import com.jgbravo.moneymate.user.ui.components.SmallSpacer
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.EmailChanged
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.OnBackClick
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.OnForgotPasswordButtonClick
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordState.Event.OnForgotPasswordFailure
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordState.Event.OnForgotPasswordSuccess
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordState.Event.ShowError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
    onForgotPasswordSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.event) {
        when (state.event) {
            is OnForgotPasswordFailure -> {
                Toast.makeText(context, state.event.errorMessage, Toast.LENGTH_LONG).show()
            }
            is ShowError -> {
                Toast.makeText(context, state.event.message, Toast.LENGTH_LONG).show()
            }
            OnForgotPasswordSuccess -> {
                Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show()
                onForgotPasswordSuccess()
            }
            null -> Unit
        }
        onAction(ForgotPasswordAction.ResetEvent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.forgot_password))
                },
                navigationIcon = {
                    BackIcon(
                        navigateBack = {
                            onAction(OnBackClick)
                        }
                    )
                }
            )
        }
    ) { padding ->
        val keyboard = LocalSoftwareKeyboardController.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilledTextField(
                label = R.string.email,
                text = state.emailText,
                onTextChange = { newValue ->
                    onAction(EmailChanged(newValue))
                },
                error = state.errorEmailText,
                modifier = Modifier.fillMaxWidth()
            )
            SmallSpacer()
            Button(
                onClick = {
                    keyboard?.hide()
                    onAction(OnForgotPasswordButtonClick)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.reset_password),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(
        state = ForgotPasswordState(),
        onAction = {},
        onForgotPasswordSuccess = {}
    )
}