package com.jgbravo.moneymate.user.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.user.ui.LoginAction
import com.jgbravo.moneymate.user.ui.LoginAction.OnEmailTextChanged
import com.jgbravo.moneymate.user.ui.LoginAction.OnPasswordTextChanged
import com.jgbravo.moneymate.user.ui.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorLogin) {
        state.errorLogin?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onAction(LoginAction.CleanErrorLogin)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        OutlinedTextField(
            label = { Text("Email") },
            value = state.emailText,
            onValueChange = {
                onAction(OnEmailTextChanged(it))
            },
            isError = state.errorEmailText != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            label = { Text("Password") },
            value = state.passwordText,
            onValueChange = {
                onAction(OnPasswordTextChanged(it))
            },
            isError = state.errorPasswordText != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onAction(LoginAction.OnLoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onAction(LoginAction.OnGoogleLoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in with Google")
        }
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    MoneyMateTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}