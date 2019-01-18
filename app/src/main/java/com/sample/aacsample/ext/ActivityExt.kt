package com.sample.aacsample.ext

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.sample.aacsample.R
import com.sample.aacsample.ui.activity.MainActivity

fun FragmentActivity.openAppInfo() {
    try {
        startActivity(Intent().also {
            it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            it.data = Uri.parse("package:" + applicationContext!!.packageName)
        })
    } catch (e: ActivityNotFoundException) {}
}

fun FragmentActivity.addFragment(fragment: Fragment) {
    Log.d(TAG, "addFragment: " + fragment.TAG)
    supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, fragment.TAG)
            .commit()
}

fun FragmentActivity.pushFragment(fragment: Fragment) {
    Log.d(TAG, "pushFragment: " + fragment.TAG)
    val type = TransitionType.PUSH
    supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(type.enter, type.exit, type.popEnter, type.popExit)
            .replace(R.id.container, fragment, fragment.TAG)
            .addToBackStack(fragment.TAG)
            .commit()
}

inline fun <reified T : FragmentActivity>
        FragmentActivity.pushFragment(bundle: Bundle = Bundle()) {
    Log.d(TAG, "pushFragment: " + bundle.toString())
    transit<T>(bundle, TransitionType.PUSH)
}

inline fun <reified T : FragmentActivity>
        FragmentActivity.modalFragment(bundle: Bundle = Bundle()) {
    Log.d(TAG, "modalFragment: " + bundle.toString())
    transit<T>(bundle, TransitionType.MODAL)
}

inline fun <reified T : FragmentActivity> FragmentActivity.transit(
    bundle: Bundle,
    anim: TransitionType
) {
    startActivity(
        Intent(this, T::class.java).apply {
            putExtra(MODAL_BUNDLE, bundle)
            putExtra(MODAL_FINISH_ENTER, anim.popEnter)
            putExtra(MODAL_FINISH_EXIT, anim.popExit)
        },
        ActivityOptionsCompat.makeCustomAnimation(this, anim.enter, anim.exit).toBundle())
}

fun FragmentActivity.selectTab(tag: String) {
    when (this) {
        is MainActivity -> selectTab(tag)
        else -> {
            startActivity(
                Intent(this, MainActivity::class.java).apply {
                    putExtra(MainActivity.BUNDLE_TAB_TAG, tag)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
    }
}

inline fun <reified T : Fragment> FragmentActivity.findFragmentBy() =
        supportFragmentManager.findFragmentByTag(T::class.java.simpleName) as? T

enum class TransitionType(val enter: Int, val exit: Int, val popEnter: Int, val popExit: Int) {
    NONE(0, 0, 0, 0),
    NO_ANIMATION(R.anim.no_anim, R.anim.no_anim, R.anim.no_anim, R.anim.no_anim),
    PUSH(R.anim.side_right_in, R.anim.side_left_out, R.anim.side_left_in, R.anim.side_right_out),
    MODAL(R.anim.scale_fade_in, R.anim.no_anim, R.anim.no_anim, R.anim.scale_fade_out),
    MODAL2(R.anim.modal_in, R.anim.no_anim, R.anim.no_anim, R.anim.modal_out),
}

const val MODAL_BUNDLE = "MODAL_BUNDLE"
const val MODAL_FINISH_ENTER = "MODAL_FINISH_ENTER"
const val MODAL_FINISH_EXIT = "MODAL_FINISH_EXIT"
