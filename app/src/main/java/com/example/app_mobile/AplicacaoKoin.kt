package com.example.app_mobile

import android.app.Application
import com.example.app_mobile.application.di.moduloGeral
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AplicacaoKoin : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@AplicacaoKoin)

            modules(moduloGeral)
        }
    }
}