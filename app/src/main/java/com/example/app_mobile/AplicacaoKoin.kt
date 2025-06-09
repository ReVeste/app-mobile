package com.example.app_mobile

import android.app.Application
import com.cloudinary.android.MediaManager
import com.example.app_mobile.application.di.moduloGeral
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AplicacaoKoin : Application() {

    override fun onCreate() {
        super.onCreate()
        setupCloudinary()

        startKoin {
            androidLogger()

            androidContext(this@AplicacaoKoin)

            modules(moduloGeral)
        }
    }

    private fun setupCloudinary() {
        val config: MutableMap<String, Any> = HashMap()
        config["cloud_name"] = "dgfur0b5h"
        config["api_key"] = "392446185274254"
        config["api_secret"] = "krqioPyuXiTNc5PArMyvZ-EqmDo"

        MediaManager.init(this, config)
    }
}