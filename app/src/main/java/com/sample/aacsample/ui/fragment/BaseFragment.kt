package com.sample.aacsample.ui.fragment

import android.support.v4.app.Fragment
import com.sample.aacsample.ui.activity.BaseActivity

/**
 * Created by y_hisano on 2018/08/16.
 */
open class BaseFragment : Fragment() {
    fun getFragmentTag() = this::class.java.simpleName!!

    fun getTransitionManager() = (activity as BaseActivity).transitionManager
}