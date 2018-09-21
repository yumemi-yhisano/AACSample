package com.sample.aacsample.ui.activity

import android.support.v7.app.AppCompatActivity
import com.sample.aacsample.ui.TransitionManager
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by y_hisano on 2018/08/28.
 */
open class BaseActivity : AppCompatActivity() {
    val transitionManager: TransitionManager by inject { parametersOf(this) }
}