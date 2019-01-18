package com.sample.aacsample.ui.fragment

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.core.CategorizedTab
import com.sample.aacsample.core.TabManager
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.databinding.FragmentMain2Binding
import com.sample.aacsample.ext.modalFragment
import com.sample.aacsample.ext.openAppInfo
import com.sample.aacsample.ext.selectTab
import com.sample.aacsample.ui.activity.DetailActivity
import com.sample.aacsample.ui.activity.TabEditActivity
import org.koin.android.ext.android.inject

/**
 * Created by y_hisano on 2018/07/24.
 */
class Main2Fragment : Fragment() {

    private lateinit var binding: FragmentMain2Binding
    private val tabManager: TabManager by inject()

    companion object {
        private const val ARG_TAG = "ARG_TAG"

        fun newInstance(tag: String) =
                Main2Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TAG, tag)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabManager.loadTabs()
        tabManager.tabs.observe(this, Observer {
            if (it != null) {
                Log.d("__DEBUG__", "onChanged")
                binding.viewpager.adapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentMain2Binding>(inflater, R.layout.fragment_main2, container, false)

        binding.viewpager.adapter = MainPagerAdapter(childFragmentManager, tabManager)
        binding.tablayout.setupWithViewPager(binding.viewpager)
        binding.tablayout.tabMode = TabLayout.MODE_SCROLLABLE

        binding.toolbar?.title?.text = ("NEWS")
        binding.toolbar?.leftArrow?.visibility = View.GONE

        binding.toolbar?.menu?.setOnClickListener {
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                binding.drawer.openDrawer(GravityCompat.START)
            }
        }
        binding.drawerNavigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_home -> {
                    activity?.selectTab(Category.general.name)
                }
                R.id.drawer_clipped -> {
                    activity?.selectTab("clipped")
                }
                R.id.drawer_tab_edit -> {
                    activity?.modalFragment<TabEditActivity>()
                }
                R.id.drawer_license -> {
                    activity?.modalFragment<DetailActivity>(
                        DetailFragment.createModal("file:///android_asset/licenses.html", "ライセンス"))
                }
                R.id.drawer_app_info -> {
                    activity?.openAppInfo()
                }
            }
            binding.drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener false
        }
        if (savedInstanceState == null) {
            arguments?.getString(ARG_TAG)?.let {
                if (it.isNotEmpty()) {
                    selectTab(it)
                }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    fun selectTab(tag: String) {
        tabManager.indexOf(tag).let { index ->
            if (index > -1) {
                binding.viewpager.currentItem = index
            }
        }
    }
}

class MainPagerAdapter(private val fragmentManager: FragmentManager, private val tabManager: TabManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
            tabManager.tabs.value?.get(position).let {
                when (it) {
                    is CategorizedTab -> NewsListFragment.newInstance(it.getCategory())
                    else -> ClippedNewsListFragment.newInstance()
                }
            }

    override fun getCount() = tabManager.tabs.value?.size ?: 0

    override fun getPageTitle(position: Int) = tabManager.tabs.value?.get(position)?.tag

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}
