package com.example.stidyretrofitmoviebase

import android.app.Application
import com.example.stidyretrofitmoviebase.di.dataModule
import com.example.stidyretrofitmoviebase.di.domainModule
import com.example.stidyretrofitmoviebase.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    uiModule,
                )
            )
        }
    }

}