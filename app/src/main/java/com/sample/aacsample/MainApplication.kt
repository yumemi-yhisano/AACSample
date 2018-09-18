package com.sample.aacsample

import android.app.Application
import com.sample.aacsample.core.AppEnv
import com.sample.aacsample.core.InitializerImpl

/**
 * Created by y_hisano on 2018/08/16.
 */
class MainApplication : Application() {

    lateinit var appEnv: AppEnv

    companion object {
        private lateinit var instance :MainApplication

        fun getInstance(): MainApplication {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        appEnv = AppEnv(this, InitializerImpl(this))
    }
}