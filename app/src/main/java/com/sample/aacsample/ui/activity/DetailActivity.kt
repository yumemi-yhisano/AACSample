package com.sample.aacsample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sample.aacsample.R
import com.sample.aacsample.databinding.ActivityMainBinding
import com.sample.aacsample.ui.TransitionManager.Companion.MODAL_BUNDLE
import com.sample.aacsample.ui.TransitionManager.Companion.MODAL_FINISH_ENTER
import com.sample.aacsample.ui.TransitionManager.Companion.MODAL_FINISH_EXIT
import com.sample.aacsample.ui.fragment.DetailFragment

/**
 * Created by y_hisano on 2018/08/28.
 */
class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = DetailFragment.newInstance(intent.getBundleExtra(MODAL_BUNDLE))
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, fragment.getFragmentTag())
                    .commit()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
                intent.getIntExtra(MODAL_FINISH_ENTER, 0),
                intent.getIntExtra(MODAL_FINISH_EXIT, 0))
    }
}