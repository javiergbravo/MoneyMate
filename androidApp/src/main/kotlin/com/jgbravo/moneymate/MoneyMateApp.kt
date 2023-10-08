package com.jgbravo.moneymate

import android.app.Application
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.di.uiModule
import com.jgbravo.moneymate.user.di.signInModule
import com.jgbravo.moneymate.user.di.userDataModule
import com.jgbravo.moneymate.user.di.userDomainModule
import com.jgbravo.moneymate.user.di.userUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyMateApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoneyMateApp)
            modules(
                listOf(
                    uiModule,
                    signInModule,
                    userUiModule,
                    userDomainModule,
                    userDataModule
                )
            )
        }

        Logger.init()

        Logger.d("Launch MoneyMateApp")
    }
}