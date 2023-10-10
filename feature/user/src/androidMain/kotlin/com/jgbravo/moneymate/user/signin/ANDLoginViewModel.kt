package com.jgbravo.moneymate.user.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.moneymate.core.utils.VM_SUBSCRIBE_TIME
import com.jgbravo.moneymate.core.utils.toCommonStateFlow
import com.jgbravo.moneymate.user.ui.LoginAction
import com.jgbravo.moneymate.user.ui.LoginState
import com.jgbravo.moneymate.user.ui.LoginViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ANDLoginViewModel(
    val loginManager: LoginManager
) : ViewModel() {

    private val viewModel by lazy {
        LoginViewModel(coroutineScope = viewModelScope)
    }

    val state = viewModel.state.combine(loginManager.loginState) { loginState, signInState ->
        loginState.copy(
            isUserLoggedIn = signInState.isUserLoggedIn
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(VM_SUBSCRIBE_TIME),
        initialValue = LoginState()
    ).toCommonStateFlow()

    fun onAction(event: LoginAction) {
        viewModel.onAction(event)
    }
}