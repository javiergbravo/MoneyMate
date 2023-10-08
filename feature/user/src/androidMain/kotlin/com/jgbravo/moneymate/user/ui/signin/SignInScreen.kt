package com.jgbravo.moneymate.user.ui.signin

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgbravo.moneymate.user.R
import com.jgbravo.moneymate.user.ui.components.FilledTextField
import com.jgbravo.moneymate.user.ui.components.ProgressBar
import com.jgbravo.moneymate.user.ui.components.SmallSpacer
import com.jgbravo.moneymate.user.ui.signin.SignInAction.EmailChanged
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnForgotPasswordClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnSignInButtonClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnSignUpClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.PasswordChanged
import com.jgbravo.moneymate.user.ui.signin.SignInAction.ResetEvent
import com.jgbravo.moneymate.user.ui.signin.SignInState.Event.OnLoginFailure
import com.jgbravo.moneymate.user.ui.signin.SignInState.Event.OnLoginSuccess
import com.jgbravo.moneymate.user.ui.signin.SignInState.Event.ShowError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.event) {
        when (state.event) {
            is ShowError -> {
                Toast.makeText(context, state.event.message, Toast.LENGTH_LONG).show()
            }
            is OnLoginFailure -> {
                Toast.makeText(context, state.event.errorMessage, Toast.LENGTH_LONG).show()
            }
            OnLoginSuccess -> {
                Toast.makeText(context, R.string.login_success, Toast.LENGTH_LONG).show()
                onLoginSuccess()
            }
            null -> Unit
        }
        onAction(ResetEvent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.sign_in))
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
                    onAction(OnSignInButtonClick)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    fontSize = 15.sp
                )
            }
            Row {
                Text(
                    modifier = Modifier.clickable {
                        onAction(OnForgotPasswordClick)
                    },
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    text = stringResource(id = R.string.vertical_divider),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable {
                        onAction(OnSignUpClick)
                    },
                    text = stringResource(id = R.string.no_account),
                    fontSize = 15.sp
                )
            }
        }

        if (state.isLoading) {
            ProgressBar()
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        state = SignInState(),
        onAction = {},
        onLoginSuccess = {}
    )
}