package com.jgbravo.moneymate.user.ui.signup

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.user.R
import com.jgbravo.moneymate.user.ui.components.BackIcon
import com.jgbravo.moneymate.user.ui.components.FilledTextField
import com.jgbravo.moneymate.user.ui.components.ProgressBar
import com.jgbravo.moneymate.user.ui.components.SmallSpacer
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.EmailChanged
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnBackClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnSignInClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnSignUpButtonClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.PasswordChanged
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.ResetEvent
import com.jgbravo.moneymate.user.ui.signup.SignUpState.Event.OnSignUpFailure
import com.jgbravo.moneymate.user.ui.signup.SignUpState.Event.OnSignUpSuccess
import com.jgbravo.moneymate.user.ui.signup.SignUpState.Event.ShowError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpState,
    onAction: (SignUpAction) -> Unit,
    onSignUpSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.event) {
        when (state.event) {
            is ShowError -> {
                Toast.makeText(context, state.event.message, Toast.LENGTH_LONG).show()
            }
            is OnSignUpFailure -> {
                Toast.makeText(context, state.event.errorMessage, Toast.LENGTH_LONG).show()
            }
            OnSignUpSuccess -> {
                Toast.makeText(context, R.string.sign_in_success, Toast.LENGTH_LONG).show()
                onSignUpSuccess()
            }
            null -> Unit
        }
        onAction(ResetEvent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.sign_up))
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
            FilledTextField(
                label = R.string.password,
                text = state.passwordText,
                onTextChange = { newValue ->
                    onAction(PasswordChanged(newValue))
                },
                hideText = true,
                error = state.errorPasswordText,
                modifier = Modifier.fillMaxWidth()
            )
            SmallSpacer()
            Button(
                onClick = {
                    keyboard?.hide()
                    onAction(OnSignUpButtonClick)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    fontSize = 15.sp
                )
            }
            Text(
                modifier = Modifier.clickable {
                    onAction(OnSignInClick)
                },
                text = stringResource(id = R.string.already_user),
                fontSize = 15.sp
            )
        }

        if (state.isLoading) {
            ProgressBar()
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    MoneyMateTheme {
        SignUpScreen(
            state = SignUpState(),
            onAction = {},
            onSignUpSuccess = {}
        )
    }
}