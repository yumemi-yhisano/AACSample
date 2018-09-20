package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sample.aacsample.R
import com.sample.aacsample.databinding.FragmentTabEditBinding
import com.sample.aacsample.ui.base.TabEditor

class TabEditFragment: BaseFragment() {

    private lateinit var binding: FragmentTabEditBinding
    val tabEditor = TabEditor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_edit, container, false)

        binding.viewpager.adapter = TabEditViewPager(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewpager)

        initToolbar()
        binding.toolbar?.title?.text = ("タブ編集")

        tabEditor.copyTmpTabs()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateToolbar()
    }

    fun onBackPressed() {
        tabEditor.saveTmpTabs()
        Toast.makeText(context?.applicationContext, "save TabModel", Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        binding.toolbar?.leftArrow?.setOnClickListener {
            val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
            if (fragmentPushed) {
                getActivityFragmentManager().popBackStack()
            }
        }
        binding.toolbar?.menu?.visibility = View.GONE
    }

    private fun updateToolbar() {
        val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
        binding.toolbar?.leftArrow?.visibility = if (fragmentPushed) View.VISIBLE else View.GONE
    }

    private fun getActivityFragmentManager() = activity!!.supportFragmentManager!!
}

class TabEditViewPager(private val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment =
            when(position) {
                0 -> TabReplaceFragment()
                1 -> TabAddDeleteFragment()
                else -> throw IllegalStateException("position[$position] isn't available.")
            }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? =
            when(position) {
                0 -> "並び替え"
                1 -> "追加・削除"
                else -> throw IllegalStateException("position[$position] isn't available.")
            }
}