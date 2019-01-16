package com.sample.aacsample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.sample.aacsample.R
import com.sample.aacsample.databinding.ActivityMainBinding
import com.sample.aacsample.ext.addFragment
import com.sample.aacsample.ext.findFragmentBy
import com.sample.aacsample.ui.fragment.TabEditFragment

class TabEditActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(TabEditFragment())
        }
    }

    override fun onBackPressed() {
        findFragmentBy<TabEditFragment>()?.onBackPressed()
        super.onBackPressed()
    }
}