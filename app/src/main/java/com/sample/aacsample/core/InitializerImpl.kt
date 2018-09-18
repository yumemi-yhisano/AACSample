package com.sample.aacsample.core

import android.content.Context

class InitializerImpl(val context: Context) : Initializer {
    override fun lazyTabManager(): Lazy<TabManager> = lazy { TabManager(context) }
}