package com.jgbravo.moneymate.user.di

import com.jgbravo.moneymate.user.domain.Validator
import org.koin.dsl.module

val userDomainModule = module {
    single { Validator() }
}