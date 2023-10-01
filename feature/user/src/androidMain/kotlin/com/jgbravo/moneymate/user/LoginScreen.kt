package com.jgbravo.moneymate.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import com.jgbravo.moneymate.user.ui.LoginAction
import com.jgbravo.moneymate.user.ui.LoginAction.OnEmailTextChanged
import com.jgbravo.moneymate.user.ui.LoginAction.OnPasswordTextChanged
import com.jgbravo.moneymate.user.ui.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorLogin) {
        state.errorLogin?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onEvent(LoginAction.CleanErrorLogin)
        }
    }

    Column(
        modifier = Modifier
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
                onEvent(OnEmailTextChanged(it))
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
                onEvent(OnPasswordTextChanged(it))
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
                onEvent(LoginAction.OnLoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in")
        }
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(),
        onEvent = {}
    )
}