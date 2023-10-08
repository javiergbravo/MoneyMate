package com.jgbravo.moneymate.user.signin

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginManager {

    private val _loginState = MutableStateFlow(SignInState())
    val loginState: StateFlow<SignInState> = _loginState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _loginState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _loginState.update { SignInState() }
    }
}