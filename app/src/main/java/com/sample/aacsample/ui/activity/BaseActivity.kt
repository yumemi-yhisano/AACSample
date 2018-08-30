package com.sample.aacsample.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sample.aacsample.ui.TransitionManager

/**
 * Created by y_hisano on 2018/08/28.
 */
open class BaseActivity : AppCompatActivity() {
    lateinit var transitionManager: TransitionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transitionManager = TransitionManager(this)
    }
}