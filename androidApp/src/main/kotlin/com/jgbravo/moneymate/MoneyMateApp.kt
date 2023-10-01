package com.jgbravo.moneymate

import android.app.Application
import com.jgbravo.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyMateApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoneyMateApp)
        }

        Logger.init()

        Logger.d("Launch MoneyMateApp")
    }
}