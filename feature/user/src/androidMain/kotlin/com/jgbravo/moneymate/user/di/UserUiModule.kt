package com.jgbravo.moneymate.user.di

import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordViewModel
import com.jgbravo.moneymate.user.ui.signin.SignInViewModel
import com.jgbravo.moneymate.user.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userUiModule = module {
    viewModel { SignInViewModel(validator = get(), repo = get()) }
    viewModel { SignUpViewModel(validator = get(), repo = get()) }
    viewModel { ForgotPasswordViewModel(validator = get(), repo = get()) }
}