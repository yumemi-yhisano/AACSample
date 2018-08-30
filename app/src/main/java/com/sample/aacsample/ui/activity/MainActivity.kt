package com.sample.aacsample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sample.aacsample.R
import com.sample.aacsample.databinding.ActivityMainBinding
import com.sample.aacsample.ui.activity.BaseActivity
import com.sample.aacsample.ui.fragment.Main2Fragment

/**
 * Created by y_hisano on 2018/07/24.
 */
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
//            val fragment = MainFragment.newInstance(Category.general)
            val fragment = Main2Fragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, fragment.getFragmentTag())
                    .commit()
        }
    }
}