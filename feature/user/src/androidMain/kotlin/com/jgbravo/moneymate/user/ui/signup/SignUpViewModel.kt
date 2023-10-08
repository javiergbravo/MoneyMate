package com.jgbravo.moneymate.user.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.errorprone.annotations.Immutable
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.core.data.Response
import com.jgbravo.moneymate.core.utils.EMPTY_STRING
import com.jgbravo.moneymate.user.data.AuthRepository
import com.jgbravo.moneymate.user.domain.Validator
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsNotValid
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsValid
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.EmailChanged
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.FocusOnEmail
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.FocusOnPassword
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnBackClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnSignInClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnSignUpButtonClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.PasswordChanged
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.ResetEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val validator: Validator,
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onAction(action: SignUpAction) {
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
                lastState.copy(focus = SignUpState.FocusTextField.EMAIL)
            }
            FocusOnPassword -> _state.update { lastState ->
                lastState.copy(focus = SignUpState.FocusTextField.PASSWORD)
            }
            OnSignUpButtonClick -> checkFieldsAndSignUp()
            OnSignInClick,
            OnBackClick -> Unit
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

    private fun checkFieldsAndSignUp() {
        _state.update { lastState ->
            lastState.copy(
                errorEmailText = checkEmailError(state.value.emailText),
                errorPasswordText = checkPasswordError(state.value.passwordText)
            )
        }

        if (state.value.errorEmailText != null || state.value.errorPasswordText != null) return

        _state.update {
            it.copy(
                isLoading = true,
                focus = SignUpState.FocusTextField.NONE
            )
        }
        viewModelScope.launch {
            val response = signUpWithEmailAndPassword(
                state.value.emailText,
                state.value.passwordText
            )
            _state.update { it.copy(isLoading = false) }
            when (response) {
                is Response.Success -> _state.update {
                    it.copy(event = SignUpState.Event.OnSignUpSuccess)
                }
                is Response.Failure -> _state.update {
                    it.copy(
                        event = SignUpState.Event.OnSignUpFailure(
                            errorMessage = response.exception.message ?: EMPTY_STRING
                        )
                    )
                }
            }
        }
    }

    private suspend fun signUpWithEmailAndPassword(email: String, password: String): Response<Any> {
        return repo.firebaseSignUpWithEmailAndPassword(email, password)
    }
}

@Immutable
data class SignUpState(
    val isLoading: Boolean = false,
    val emailText: String = EMPTY_STRING,
    val passwordText: String = EMPTY_STRING,
    @StringRes val errorEmailText: Int? = null,
    @StringRes val errorPasswordText: Int? = null,
    val focus: FocusTextField = FocusTextField.NONE,
    val event: Event? = null
) {
    sealed interface Event {
        data object OnSignUpSuccess : Event
        data class OnSignUpFailure(val errorMessage: String) : Event // TODO: change to @StringRes
        data class ShowError(@StringRes val message: Int) : Event
    }

    enum class FocusTextField {
        NONE, EMAIL, PASSWORD
    }
}

sealed interface SignUpAction {
    data object ResetEvent : SignUpAction
    data object FocusOnEmail : SignUpAction
    data object FocusOnPassword : SignUpAction
    data class EmailChanged(val email: String) : SignUpAction
    data class PasswordChanged(val password: String) : SignUpAction
    data object OnSignUpButtonClick : SignUpAction
    data object OnSignInClick : SignUpAction
    data object OnBackClick : SignUpAction
}