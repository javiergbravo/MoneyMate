package com.jgbravo.moneymate.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.moneymate.user.ui.LoginAction
import com.jgbravo.moneymate.user.ui.LoginViewModel

class ANDLoginViewModel : ViewModel() {

    private val viewModel by lazy {
        LoginViewModel(
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: LoginAction) {
        viewModel.onEvent(event)
    }
}