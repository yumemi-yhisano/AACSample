package com.sample.aacsample.core

interface Initializer {
    fun lazyTabManager() :Lazy<TabManager>
}