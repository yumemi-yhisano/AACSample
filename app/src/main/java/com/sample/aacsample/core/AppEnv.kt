package com.sample.aacsample.core

import android.content.Context

class AppEnv(val context: Context, initializer: Initializer) {
    val tabManager: TabManager by initializer.lazyTabManager()
}