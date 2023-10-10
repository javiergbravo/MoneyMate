package com.jgbravo.moneymate.user.signin

import com.jgbravo.moneymate.user.models.UserData
import com.jgbravo.moneymate.user.signin.AuthResult.Error
import com.jgbravo.moneymate.user.signin.AuthResult.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginManager {

    private val _loginState = MutableStateFlow(AuthState())
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    fun onSignInResult(result: AuthResult) {
        when (result) {
            is Error -> _loginState.update {
                it.copy(
                    isUserLoggedIn = false,
                    loginError = result.errorMessage
                )
            }
            is Success -> _loginState.update {
                it.copy(
                    isUserLoggedIn = true,
                    loginError = null
                )
            }
        }
    }

    fun resetState() {
        _loginState.update { AuthState() }
    }
}

sealed interface AuthResult {
    data class Success(val data: UserData) : AuthResult
    data class Error(val errorMessage: String) : AuthResult
}

data class AuthState(
    val isUserLoggedIn: Boolean = false,
    val loginError: String? = null,
)
