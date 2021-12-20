package com.enyason.edvoraapp

import android.app.Application
import com.enyason.edvoraapp.core.di.coreModule
import com.enyason.edvoraapp.presentation.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class EdvoraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EdvoraApp)
            modules(coreModule, uiModule)
        }
    }
}