package com.example.test

import android.app.Application
import com.example.test.di.networkModule
import com.example.test.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    viewModelsModule
                )
            )
        }
    }
}