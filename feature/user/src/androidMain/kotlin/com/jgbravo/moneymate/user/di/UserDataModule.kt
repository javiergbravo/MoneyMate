package com.jgbravo.moneymate.user.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jgbravo.moneymate.user.data.AuthRepository
import com.jgbravo.moneymate.user.data.AuthRepositoryImpl
import org.koin.dsl.module

val userDataModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<AuthRepository> { AuthRepositoryImpl(auth = get()) }
}