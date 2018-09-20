package com.sample.aacsample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sample.aacsample.R
import com.sample.aacsample.databinding.ActivityMainBinding
import com.sample.aacsample.ui.fragment.TabEditFragment

class TabEditActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = TabEditFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, fragment.getFragmentTag())
                    .commit()
        }
    }

    override fun onBackPressed() {
        getTabEditFragment()?.onBackPressed()
        super.onBackPressed()
    }

    private fun getTabEditFragment() = supportFragmentManager.findFragmentByTag(TabEditFragment::class.java.simpleName) as? TabEditFragment?
}