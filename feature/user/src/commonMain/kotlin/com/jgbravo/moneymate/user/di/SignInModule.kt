package com.jgbravo.moneymate.user.di

import com.jgbravo.moneymate.user.signin.LoginManager
import org.koin.dsl.module

val signInModule = module {
    single { LoginManager() }
}