package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.MainApplication
import com.sample.aacsample.R
import com.sample.aacsample.core.CategorizedTab
import com.sample.aacsample.core.TabManager
import com.sample.aacsample.databinding.FragmentMain2Binding
import com.sample.aacsample.ui.activity.DetailActivity
import com.sample.aacsample.util.ActivityUtil

/**
 * Created by y_hisano on 2018/07/24.
 */
class Main2Fragment : BaseFragment() {

    private lateinit var binding: FragmentMain2Binding
    private val tabManager = MainApplication.getInstance().appEnv.tabManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabManager.loadTabs()
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
            } else{
                binding.drawer.openDrawer(GravityCompat.START)
            }
        }
        binding.drawerNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.drawer_home -> {}
                R.id.drawer_clipped -> {}
                R.id.drawer_license -> {
                    getTransitionManager().modal(
                            DetailFragment.createModal(
                                    "file:///android_asset/licenses.html", "ライセンス"),
                            DetailActivity::class.java)
                }
                R.id.drawer_app_info -> {
                    ActivityUtil.openAppInfo(activity)
                }
            }
            binding.drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener false
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
}

class MainPagerAdapter(private val fragmentManager: FragmentManager, private val tabManager: TabManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
            tabManager.tabs[position].let {
                when (it) {
                    is CategorizedTab -> NewsListFragment.newInstance(it.getCategory())
                    else -> ClippedNewsListFragment.newInstance()
                }
            }

    override fun getCount() = tabManager.tabs.size

    override fun getPageTitle(position: Int) = tabManager.tabs[position].tag
}