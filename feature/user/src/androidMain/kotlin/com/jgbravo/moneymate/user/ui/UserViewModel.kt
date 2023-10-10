package com.jgbravo.moneymate.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.moneymate.user.data.AuthRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
    init {
        getAuthState()
    }

    fun getAuthState() = repo.getAuthState(viewModelScope)

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false
}