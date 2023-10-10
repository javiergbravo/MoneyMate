package com.jgbravo.moneymate.di

import com.jgbravo.moneymate.user.signin.ANDLoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel<ANDLoginViewModel> { ANDLoginViewModel(get()) }
}