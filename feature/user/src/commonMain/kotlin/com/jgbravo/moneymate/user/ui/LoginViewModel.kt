package com.jgbravo.moneymate.user.ui

import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.utils.EMPTY_STRING
import com.jgbravo.moneymate.utils.VM_SUBSCRIBE_TIME
import com.jgbravo.moneymate.utils.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(VM_SUBSCRIBE_TIME),
            initialValue = LoginState()
        )
        .toCommonStateFlow()

    fun onEvent(event: LoginAction) {
        when (event) {
            is LoginAction.OnEmailTextChanged -> {
                Logger.d("LoginEvent.OnEmailTextChanged - email=${event.text}")
                _state.update {
                    it.copy(
                        emailText = event.text,
                        errorEmailText = if (validateEmail(event.text)) {
                            null
                        } else {
                            "Invalid email"
                        }
                    )
                }
            }
            is LoginAction.OnPasswordTextChanged -> {
                Logger.d("LoginEvent.OnPasswordTextChanged - password=${event.text}")
                _state.update {
                    it.copy(
                        passwordText = event.text,
                        errorPasswordText = if (validatePassword(event.text)) {
                            null
                        } else {
                            "Invalid password"
                        }
                    )
                }
            }
            is LoginAction.OnLoginClicked -> {
                Logger.d("LoginEvent.OnLoginClicked")
                when {
                    validateEmail(_state.value.emailText) && validatePassword(_state.value.passwordText) -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                        login(_state.value.emailText, _state.value.passwordText)
                    }
                    else -> onEvent(LoginAction.OnLoginError("Invalid email or password"))
                }
            }
            is LoginAction.OnLoginSuccess -> {
                Logger.d("LoginEvent.OnLoginSuccess - [LOGIN SUCCESS ✅]")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorLogin = null
                    )
                }
            }
            is LoginAction.OnLoginError -> {
                Logger.d("LoginEvent.OnLoginError - error=${event.errorMessage}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorLogin = event.errorMessage
                    )
                }
            }
            is LoginAction.CleanErrorLogin -> {
                Logger.d("LoginEvent.CleanErrorLogin")
                _state.update {
                    it.copy(
                        errorLogin = null
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun login(email: String, password: String) {
        //TODO: change for real login
        onEvent(LoginAction.OnLoginSuccess)
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val emailText: String = EMPTY_STRING,
    val passwordText: String = EMPTY_STRING,
    val errorEmailText: String? = null,
    val errorPasswordText: String? = null,
    val errorLogin: String? = null
)

sealed class LoginAction {
    data class OnEmailTextChanged(val text: String) : LoginAction()
    data class OnPasswordTextChanged(val text: String) : LoginAction()
    data object OnLoginClicked : LoginAction()
    data object OnLoginSuccess : LoginAction()
    data class OnLoginError(val errorMessage: String) : LoginAction()
    data object CleanErrorLogin : LoginAction()
}