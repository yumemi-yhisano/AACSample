package com.sample.aacsample.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.sample.aacsample.R
import com.sample.aacsample.data.PrefKey
import com.sample.aacsample.data.Preferences
import com.sample.aacsample.databinding.ActivityStartupBinding
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class StartUpActivity : BaseActivity() {

    private lateinit var binding: ActivityStartupBinding
    private var resumed = false
    private var pendingTransitMain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)

        launch {
            Log.d("StartUpActivity", "launch START")
            delay(1000)
            Log.d("StartUpActivity", "launch END")
            val initialized = Preferences.getVal(this@StartUpActivity.applicationContext, PrefKey.INITIALIZED, Boolean::class.java)
            if (initialized) {
                if (resumed) {
                    transitMain()
                } else {
                    pendingTransitMain = true
                }
                return@launch
            }
            runOnUiThread{ binding.message.text = "Initializing..." }
            delay(1000)
            Preferences.putVal(this@StartUpActivity.applicationContext, PrefKey.INITIALIZED, true)
            runOnUiThread{ binding.message.text = "Completed!" }
            if (resumed) {
                transitMain()
            } else {
                pendingTransitMain = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumed = true
        if (pendingTransitMain) {
            pendingTransitMain = false
            transitMain()
        }
    }

    override fun onPause() {
        super.onPause()
        resumed = false
    }

    private fun transitMain() {
        startActivity(Intent(this@StartUpActivity, MainActivity::class.java))
        finish()
    }
}