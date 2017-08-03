package com.chicago311

import android.app.Application
import com.chicago311.di.AppComponent
import com.chicago311.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class ChicagoApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        appComponent = DaggerAppComponent.builder().build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        }
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}