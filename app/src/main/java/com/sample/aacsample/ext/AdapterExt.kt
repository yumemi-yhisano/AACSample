package com.sample.aacsample.ext

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

fun <T: RecyclerView.ViewHolder, T2: ViewDataBinding> RecyclerView.Adapter<T>.createBinding(@LayoutRes layoutRes: Int, parent: ViewGroup) =
        DataBindingUtil.inflate<T2>(LayoutInflater.from(parent.context), layoutRes, parent, false)!!
