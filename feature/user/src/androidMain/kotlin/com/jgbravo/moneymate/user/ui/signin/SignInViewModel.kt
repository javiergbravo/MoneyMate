package com.jgbravo.moneymate.user.ui.signin

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.core.data.Result
import com.jgbravo.moneymate.core.utils.EMPTY_STRING
import com.jgbravo.moneymate.user.data.AuthRepository
import com.jgbravo.moneymate.user.domain.Validator
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsNotValid
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsValid
import com.jgbravo.moneymate.user.ui.signin.SignInAction.EmailChanged
import com.jgbravo.moneymate.user.ui.signin.SignInAction.FocusOnEmail
import com.jgbravo.moneymate.user.ui.signin.SignInAction.FocusOnPassword
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnForgotPasswordClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnSignInButtonClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnSignUpClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.PasswordChanged
import com.jgbravo.moneymate.user.ui.signin.SignInAction.ResetEvent
import com.jgbravo.moneymate.user.ui.signin.SignInState.Event.OnLoginFailure
import com.jgbravo.moneymate.user.ui.signin.SignInState.Event.OnLoginSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validator: Validator,
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onAction(action: SignInAction) {
        Logger.d("[ACTION] $action")
        when (action) {
            ResetEvent -> _state.update { it.copy(event = null) }
            is EmailChanged -> _state.update { lastState ->
                lastState.copy(
                    emailText = action.email,
                    errorEmailText = checkEmailError(action.email)
                )
            }
            is PasswordChanged -> _state.update { lastState ->
                lastState.copy(
                    passwordText = action.password,
                    errorPasswordText = checkPasswordError(action.password)
                )
            }
            FocusOnEmail -> _state.update { lastState ->
                lastState.copy(errorPasswordText = checkPasswordError(state.value.passwordText))
            }
            FocusOnPassword -> _state.update { lastState ->
                lastState.copy(errorEmailText = checkEmailError(state.value.emailText))
            }
            OnSignInButtonClick -> checkFieldsAndSignIn()
            OnForgotPasswordClick,
            OnSignUpClick -> Unit
        }
    }

    @StringRes
    private fun checkEmailError(email: String): Int? {
        return when (val validation = validator.validateEmail(email)) {
            IsValid -> null
            is IsNotValid -> validation.error
        }
    }

    @StringRes
    private fun checkPasswordError(password: String): Int? {
        return when (val validation = validator.validatePassword(password)) {
            IsValid -> null
            is IsNotValid -> validation.error
        }
    }

    private fun checkFieldsAndSignIn() {
        _state.update { lastState ->
            lastState.copy(
                errorEmailText = checkEmailError(state.value.emailText),
                errorPasswordText = checkPasswordError(state.value.passwordText)
            )
        }

        if (state.value.errorEmailText != null || state.value.errorPasswordText != null) return

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repo.firebaseSignInWithEmailAndPassword(state.value.emailText, state.value.passwordText)
            _state.update { lastState ->
                lastState.copy(
                    isLoading = false,
                    event = when (response) {
                        Result.Success -> OnLoginSuccess
                        is Result.Failure -> OnLoginFailure(errorMessage = response.exception.message ?: EMPTY_STRING)
                    }
                )
            }
        }
    }
}

@Immutable
data class SignInState(
    val isLoading: Boolean = false,
    val emailText: String = EMPTY_STRING,
    val passwordText: String = EMPTY_STRING,
    @StringRes val errorEmailText: Int? = null,
    @StringRes val errorPasswordText: Int? = null,
    val event: Event? = null
) {

    sealed interface Event {
        data object OnLoginSuccess : Event
        data class OnLoginFailure(val errorMessage: String) : Event // TODO: change to @StringRes
        data class ShowError(@StringRes val message: Int) : Event
    }
}

sealed interface SignInAction {
    data object ResetEvent : SignInAction
    data object FocusOnEmail : SignInAction
    data object FocusOnPassword : SignInAction
    data class EmailChanged(val email: String) : SignInAction
    data class PasswordChanged(val password: String) : SignInAction
    data object OnSignInButtonClick : SignInAction
    data object OnForgotPasswordClick : SignInAction
    data object OnSignUpClick : SignInAction
}