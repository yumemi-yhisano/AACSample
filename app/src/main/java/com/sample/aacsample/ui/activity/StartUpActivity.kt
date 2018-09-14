package com.sample.aacsample.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.sample.aacsample.R
import com.sample.aacsample.data.PrefKey
import com.sample.aacsample.data.Preferences
import com.sample.aacsample.databinding.ActivityStartupBinding
import com.sample.aacsample.ui.base.PausableDispatcher
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class StartUpActivity : BaseActivity() {

    private lateinit var binding: ActivityStartupBinding
    private val dispatcher = PausableDispatcher(Handler(Looper.getMainLooper()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)

        launch(dispatcher) {
            Log.d("StartUpActivity", "launch START")
            delay(1000)
            val initialized = Preferences.getVal(this@StartUpActivity.applicationContext, PrefKey.INITIALIZED, Boolean::class.java)
            val loaded = Preferences.getVal(this@StartUpActivity.applicationContext, PrefKey.LOADED, Boolean::class.java)
            if (! initialized) {
                initialize()
            }
            if (!loaded) {
                load(coroutineContext).join()
            }
            runOnUiThread { binding.message.text = "Completed!" }
            Log.d("StartUpActivity", "Completed!")
            transitMain()
            Log.d("StartUpActivity", "launch END")
        }
    }

    override fun onResume() {
        super.onResume()
        dispatcher.resume()
    }

    override fun onPause() {
        dispatcher.pause()
        super.onPause()
    }

    private suspend fun initialize() {
        runOnUiThread{ binding.message.text = "Initializing..." }
        Log.d("StartUpActivity", "Initializing...")
        delay(2000)
        Preferences.putVal(this@StartUpActivity.applicationContext, PrefKey.INITIALIZED, true)
        Log.d("StartUpActivity", "Initialized!")
    }

    private suspend fun load(parentContext: CoroutineContext) =
//        launch(CommonPool + parentContext) { // pausable を有効にしたい場合親の CoroutineContext を渡す
        launch {
            runOnUiThread { binding.message.text = "Downloading..." }
            Log.d("StartUpActivity", "Downloading...")
            delay(2000)
            runOnUiThread { binding.message.text = "Loading..." }
            Log.d("StartUpActivity", "Loading...")
            delay(2000)
            Preferences.putVal(this@StartUpActivity.applicationContext, PrefKey.LOADED, true)
            Log.d("StartUpActivity", "Loaded!")
        }

    private fun transitMain() {
        startActivity(Intent(this@StartUpActivity, MainActivity::class.java))
        finish()
    }
}