package com.sample.aacsample

import android.app.Application

/**
 * Created by y_hisano on 2018/08/16.
 */
class MainApplication : Application() {

    companion object {
        private lateinit var instance :MainApplication

        fun getInstance(): MainApplication {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this;
    }
}