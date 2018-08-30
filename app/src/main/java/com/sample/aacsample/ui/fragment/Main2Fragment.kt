package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.databinding.FragmentMain2Binding

/**
 * Created by y_hisano on 2018/07/24.
 */
class Main2Fragment : BaseFragment() {

    private lateinit var binding: FragmentMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentMain2Binding>(inflater, R.layout.fragment_main2, container, false)

        binding.viewpager.adapter = MainPagerAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewpager)
        binding.tablayout.tabMode = TabLayout.MODE_SCROLLABLE

        binding.toolbar?.title?.text = ("NEWS")
        binding.toolbar?.leftArrow?.visibility = View.GONE

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
}

class MainPagerAdapter(private val fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val tabs = Tabs()

    override fun getItem(position: Int) =
            if (tabs.isCategory(position)) NewsListFragment.newInstance(Category.values()[position])
            else ClippedNewsListFragment.newInstance()

    override fun getCount() = tabs.size()

    override fun getPageTitle(position: Int) = tabs.pageTitle(position)
}

class Tabs {
    fun size() = Category.values().size + 1

    fun pageTitle(position: Int) =
            if (isCategory(position)) Category.values()[position].name
            else "clipped"

    fun isCategory(position: Int) = Category.values().size > position
}