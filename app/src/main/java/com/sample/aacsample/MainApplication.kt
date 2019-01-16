package com.sample.aacsample

import android.app.Application
import com.sample.aacsample.di.mainApp
import org.koin.android.ext.android.startKoin

/**
 * Created by y_hisano on 2018/08/16.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, mainApp)
    }
}