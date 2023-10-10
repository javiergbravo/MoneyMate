package com.jgbravo.moneymate.user.ui.forgotpassword

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.core.data.Result
import com.jgbravo.moneymate.core.utils.EMPTY_STRING
import com.jgbravo.moneymate.user.data.AuthRepository
import com.jgbravo.moneymate.user.domain.Validator
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsNotValid
import com.jgbravo.moneymate.user.domain.Validator.ValidatorResult.IsValid
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.EmailChanged
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.FocusOnEmail
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.OnBackClick
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.OnForgotPasswordButtonClick
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction.ResetEvent
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordState.Event.OnForgotPasswordFailure
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordState.Event.OnForgotPasswordSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val validator: Validator,
    private val repo: AuthRepository
) : ViewModel(
) {

    private val _state = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onAction(action: ForgotPasswordAction) {
        Logger.d("[ACTION] $action")
        when (action) {
            ResetEvent -> _state.update { it.copy(event = null) }
            is EmailChanged -> _state.update {
                it.copy(
                    emailText = action.email,
                    errorEmailText = checkEmailError(action.email)
                )
            }
            FocusOnEmail -> _state.update {
                it.copy(
                    isFocusOnEmail = true,
                    errorEmailText = if (it.emailText.isEmpty()) null else checkEmailError(it.emailText)
                )
            }
            OnForgotPasswordButtonClick -> checkFieldAndSendEmail()
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

    private fun checkFieldAndSendEmail() {
        _state.update { lastState ->
            lastState.copy(errorEmailText = checkEmailError(state.value.emailText))
        }

        if (state.value.errorEmailText != null) return

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repo.sendPasswordResetEmail(state.value.emailText)
            _state.update { lastState ->
                lastState.copy(
                    isLoading = false,
                    event = when (response) {
                        Result.Success -> OnForgotPasswordSuccess
                        is Result.Failure -> OnForgotPasswordFailure(
                            errorMessage = response.exception.message ?: EMPTY_STRING
                        )
                    }
                )
            }
        }
    }
}

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val emailText: String = EMPTY_STRING,
    val isFocusOnEmail: Boolean = false,
    @StringRes val errorEmailText: Int? = null,
    val event: Event? = null
) {
    sealed interface Event {
        data object OnForgotPasswordSuccess : Event
        data class OnForgotPasswordFailure(val errorMessage: String) : Event
        data class ShowError(@StringRes val message: Int) : Event
    }
}

sealed interface ForgotPasswordAction {
    data object ResetEvent : ForgotPasswordAction
    data object FocusOnEmail : ForgotPasswordAction
    data class EmailChanged(val email: String) : ForgotPasswordAction
    data object OnForgotPasswordButtonClick : ForgotPasswordAction
    data object OnBackClick : ForgotPasswordAction
}