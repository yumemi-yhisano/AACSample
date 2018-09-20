package com.sample.aacsample.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import com.sample.aacsample.R
import com.sample.aacsample.ui.activity.BaseActivity
import com.sample.aacsample.ui.activity.MainActivity
import com.sample.aacsample.ui.fragment.BaseFragment

/**
 * Created by y_hisano on 2018/08/16.
 */
class TransitionManager(private val activity: BaseActivity) {

    companion object {
        const val MODAL_BUNDLE = "MODAL_BUNDLE"
        const val MODAL_FINISH_ENTER = "MODAL_FINISH_ENTER"
        const val MODAL_FINISH_EXIT = "MODAL_FINISH_EXIT"

        val TAG = TransitionManager::class.java.name!!
    }

    fun push(fragment: BaseFragment) {
        Log.d(TAG, "push: " + fragment.toString())
        val type = TransitionType.PUSH
        activity.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(type.enter, type.exit, type.popEnter, type.popExit)
                .replace(R.id.container, fragment, fragment.getFragmentTag())
                .addToBackStack(fragment.getFragmentTag())
                .commit()
    }

    fun <T: BaseActivity> push(bundle: Bundle = Bundle(), clazz: Class<T>) {
        Log.d(TAG, "push: " + bundle.toString())
        transit(bundle, clazz, TransitionType.PUSH)
    }

    fun <T: BaseActivity> modal(bundle: Bundle = Bundle(), clazz: Class<T>) {
        Log.d(TAG, "modal: " + bundle.toString())
        transit(bundle, clazz, TransitionType.MODAL)
    }

    fun selectTab(tag: String) {
        when (activity) {
            is MainActivity -> activity.selectTab(tag)
            else -> {
                activity.startActivity(
                        Intent(activity, MainActivity::class.java).apply {
                            putExtra(MainActivity.BUNDLE_TAB_TAG, tag)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                )
            }
        }
    }

    private fun <T: BaseActivity> transit(bundle: Bundle, clazz: Class<T>, anim: TransitionType) {
        activity.startActivity(
                Intent(activity, clazz).apply {
                    putExtra(MODAL_BUNDLE, bundle)
                    putExtra(MODAL_FINISH_ENTER, anim.popEnter)
                    putExtra(MODAL_FINISH_EXIT, anim.popExit)
                },
                ActivityOptionsCompat.makeCustomAnimation(activity, anim.enter, anim.exit).toBundle())
    }

    enum class TransitionType(val enter: Int, val exit: Int, val popEnter: Int, val popExit: Int) {
        NONE(0, 0, 0, 0),
        NO_ANIMATION(R.anim.no_anim, R.anim.no_anim, R.anim.no_anim, R.anim.no_anim),
        PUSH(R.anim.side_right_in, R.anim.side_left_out, R.anim.side_left_in, R.anim.side_right_out),
        MODAL(R.anim.scale_fade_in, R.anim.no_anim, R.anim.no_anim, R.anim.scale_fade_out),
        MODAL2(R.anim.modal_in, R.anim.no_anim, R.anim.no_anim, R.anim.modal_out),
    }
}
