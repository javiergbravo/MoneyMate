package com.jgbravo.moneymate.user.domain

import androidx.annotation.StringRes
import com.jgbravo.moneymate.user.R

class Validator {

    fun validateEmail(email: String): ValidatorResult {
        return when {
            email.isBlank() -> ValidatorResult.IsNotValid(R.string.email_is_empty)
            email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) -> ValidatorResult.IsValid
            else -> ValidatorResult.IsNotValid(R.string.invalid_email)
        }
    }

    fun validatePassword(password: String): ValidatorResult {
        return when {
            password.isBlank() -> ValidatorResult.IsNotValid(R.string.password_is_empty)
            password.length >= 8 -> ValidatorResult.IsValid
            else -> ValidatorResult.IsNotValid(R.string.invalid_password)
        }
    }

    sealed interface ValidatorResult {
        data object IsValid : ValidatorResult
        data class IsNotValid(@StringRes val error: Int) : ValidatorResult
    }
}