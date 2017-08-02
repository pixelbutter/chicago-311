package com.chicago311

import android.app.Application
import com.chicago311.di.AppComponent
import com.chicago311.di.DaggerAppComponent

class ChicagoApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}