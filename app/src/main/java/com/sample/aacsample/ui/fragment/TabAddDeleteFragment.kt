package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.sample.aacsample.R
import com.sample.aacsample.databinding.FragmentTabReplaceBinding
import com.sample.aacsample.databinding.ViewTabEditItemBinding
import com.sample.aacsample.ui.base.TabEditor

class TabAddDeleteFragment : Fragment() {

    private lateinit var binding: FragmentTabReplaceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_replace, container, false)
        binding.recyclerView.let {
            it.adapter = TabAddDeleteViewAdaptor(getTabEditFragment().tabEditor)
            it.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    private fun getTabEditFragment() = parentFragment as TabEditFragment
}

class TabAddDeleteViewAdaptor(private val tabEditor: TabEditor) : RecyclerView.Adapter<TabAddDeleteItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TabAddDeleteItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.view_tab_edit_item,
                            parent,
                            false))

    override fun getItemCount() = tabEditor.defaultTabTags().size

    override fun onBindViewHolder(holder: TabAddDeleteItemViewHolder, position: Int) {
        tabEditor.defaultTabTags()[position].let { tag ->
            holder.binding.check.setOnCheckedChangeListener(null)
            holder.binding.title.text = tag
            holder.binding.check.isChecked = tabEditor.tmpTabs.find { it.tag == tag } != null

            when {
                tabEditor.isEditable(tag) -> {
                    holder.binding.check.visibility = View.VISIBLE
                    holder.binding.check.isEnabled = true
                }
                else -> {
                    holder.binding.check.visibility = View.INVISIBLE
                    holder.binding.check.isEnabled = false
                }
            }
            holder.binding.check.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
                when {
                    isChecked -> tabEditor.addTabToTmpTabs(tag)
                    else -> tabEditor.removeTabFromTmpTabs(tag)
                }
            }
        }
    }
}

class TabAddDeleteItemViewHolder(val binding: ViewTabEditItemBinding) : RecyclerView.ViewHolder(binding.root)
