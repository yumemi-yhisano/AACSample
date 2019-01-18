package com.sample.aacsample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.sample.aacsample.R
import com.sample.aacsample.databinding.ActivityMainBinding
import com.sample.aacsample.ext.MODAL_BUNDLE
import com.sample.aacsample.ext.MODAL_FINISH_ENTER
import com.sample.aacsample.ext.MODAL_FINISH_EXIT
import com.sample.aacsample.ext.addFragment
import com.sample.aacsample.ui.fragment.DetailFragment

/**
 * Created by y_hisano on 2018/08/28.
 */
class DetailActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(DetailFragment.newInstance(intent.getBundleExtra(MODAL_BUNDLE)))
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
                intent.getIntExtra(MODAL_FINISH_ENTER, 0),
                intent.getIntExtra(MODAL_FINISH_EXIT, 0))
    }
}
