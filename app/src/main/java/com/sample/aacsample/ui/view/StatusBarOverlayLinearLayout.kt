package com.sample.aacsample.ui.view

import android.content.Context
import android.os.Build
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class StatusBarOverlayLinearLayout : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleRes: Int) : super(context, attrs, defStyleRes)

    init {
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setOnApplyWindowInsetsListener { view, insets ->
                    insets.consumeSystemWindowInsets()
                }
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }
}