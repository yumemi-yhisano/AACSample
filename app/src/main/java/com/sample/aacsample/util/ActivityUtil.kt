package com.sample.aacsample.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.FragmentActivity

class ActivityUtil {
    companion object {
        fun openAppInfo(activity: FragmentActivity?) {
            activity?.startActivity(Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                data = Uri.parse("package:" + activity.applicationContext!!.packageName)
            })

        }
    }
}