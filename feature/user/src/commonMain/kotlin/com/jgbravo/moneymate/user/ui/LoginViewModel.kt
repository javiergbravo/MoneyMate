package com.jgbravo.moneymate.user.ui

import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.core.utils.EMPTY_STRING
import com.jgbravo.moneymate.core.utils.toCommonStateFlow
import com.jgbravo.moneymate.user.ui.LoginAction.CleanErrorLogin
import com.jgbravo.moneymate.user.ui.LoginAction.OnEmailTextChanged
import com.jgbravo.moneymate.user.ui.LoginAction.OnGoogleLoginClicked
import com.jgbravo.moneymate.user.ui.LoginAction.OnLoginClicked
import com.jgbravo.moneymate.user.ui.LoginAction.OnLoginError
import com.jgbravo.moneymate.user.ui.LoginAction.OnLoginSuccess
import com.jgbravo.moneymate.user.ui.LoginAction.OnPasswordTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO: implements in iOS like this article:
//  https://medium.com/@daniel.atitienei/how-to-implement-flows-and-share-viewmodels-in-kotlin-multiplatform-mobile-kmm-89dfe08758ef
class LoginViewModel(
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow().toCommonStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is OnEmailTextChanged -> {
                Logger.d("LoginEvent.OnEmailTextChanged - email=${action.text}")
                _state.update {
                    it.copy(
                        emailText = action.text,
                        errorEmailText = if (validateEmail(action.text)) {
                            null
                        } else {
                            "Invalid email"
                        }
                    )
                }
            }
            is OnPasswordTextChanged -> {
                Logger.d("LoginEvent.OnPasswordTextChanged - password=${action.text}")
                _state.update {
                    it.copy(
                        passwordText = action.text,
                        errorPasswordText = if (validatePassword(action.text)) {
                            null
                        } else {
                            "Invalid password"
                        }
                    )
                }
            }
            is OnLoginClicked -> {
                Logger.d("LoginEvent.OnLoginClicked")
                when {
                    validateEmail(_state.value.emailText) && validatePassword(_state.value.passwordText) -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                        login(_state.value.emailText, _state.value.passwordText)
                    }
                    else -> onAction(OnLoginError("Invalid email or password"))
                }
            }
            is OnLoginSuccess -> {
                Logger.d("LoginEvent.OnLoginSuccess - [LOGIN SUCCESS ✅]")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorLogin = null
                    )
                }
            }
            is OnLoginError -> {
                Logger.d("LoginEvent.OnLoginError - error=${action.errorMessage}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorLogin = action.errorMessage
                    )
                }
            }
            is CleanErrorLogin -> {
                Logger.d("LoginEvent.CleanErrorLogin")
                _state.update {
                    it.copy(
                        errorLogin = null
                    )
                }
            }
            OnGoogleLoginClicked -> {
                TODO()
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
        onAction(LoginAction.OnLoginSuccess)
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val isUserLoggedIn: Boolean = false,
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
    data object OnGoogleLoginClicked : LoginAction()
    data object OnLoginSuccess : LoginAction()
    data class OnLoginError(val errorMessage: String) : LoginAction()
    data object CleanErrorLogin : LoginAction()
}