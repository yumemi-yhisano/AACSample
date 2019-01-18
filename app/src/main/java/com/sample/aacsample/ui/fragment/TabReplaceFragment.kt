package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.core.TabMode
import com.sample.aacsample.databinding.FragmentTabReplaceBinding
import com.sample.aacsample.databinding.ViewTabEditItemBinding
import com.sample.aacsample.ui.base.TabEditor
import com.sample.aacsample.ui.base.TmpTabChangeObserver

class TabReplaceFragment : Fragment() {

    private lateinit var binding: FragmentTabReplaceBinding

    private val tmpTabChangeObserver = object : TmpTabChangeObserver {
        override fun onInsert(position: Int) {
            binding.recyclerView.adapter.notifyItemInserted(position)
        }

        override fun onDelete(position: Int) {
            binding.recyclerView.adapter.notifyItemRemoved(position)
        }
    }

    companion object {
        private val TAG = TabReplaceFragment::class.java.simpleName
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_replace, container, false)

        binding.recyclerView.let {
            it.adapter = TabReplaceViewAdaptor(getTabEditFragment().tabEditor)
            it.layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            private var viewHolder: RecyclerView.ViewHolder? = null

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {}

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                val pos1 = viewHolder?.adapterPosition ?: return false
                val pos2 = target?.adapterPosition ?: return false
                Log.d(TAG, "onMove from:$pos1, to:$pos2")
                getTabEditFragment().tabEditor.tmpTabs.let {
                    if (it[pos1].tabMode == TabMode.FIX_LAST || it[pos1].tabMode == TabMode.FIX_START ||
                            it[pos2].tabMode == TabMode.FIX_LAST || it[pos2].tabMode == TabMode.FIX_START) {
                        return false
                    }
                    val tmp = it[pos1]
                    it[pos1] = it[pos2]
                    it[pos2] = tmp
                }
                binding.recyclerView.adapter.notifyItemMoved(pos1, pos2)
                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                val state = when (actionState) {
                    0 -> "IDLE"
                    1 -> "SWIPE"
                    2 -> "DRAG"
                    else -> "OTHER"
                }
                Log.d(TAG, "onSelectedChanged ${viewHolder?.adapterPosition}, state:$state")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                        this.viewHolder?.itemView?.translationZ = 0f
                    } else {
                        this.viewHolder?.itemView?.translationZ = 0f
                        this.viewHolder = viewHolder
                        this.viewHolder?.itemView?.translationZ = 50f
                    }
                }
                super.onSelectedChanged(viewHolder, actionState)
            }

            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                viewHolder?.adapterPosition?.let { pos ->
                    getTabEditFragment().tabEditor.tmpTabs.let {
                        if (it[pos].tabMode == TabMode.FIX_LAST || it[pos].tabMode == TabMode.FIX_START) {
                            return ItemTouchHelper.Callback.makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.UP or ItemTouchHelper.DOWN)
                        }
                    }
                }
                return super.getMovementFlags(recyclerView, viewHolder)
            }
        }).attachToRecyclerView(binding.recyclerView)

        getTabEditFragment().tabEditor.addTmpTabChangeObserver(tmpTabChangeObserver)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        getTabEditFragment().tabEditor.tmpTabs.forEachIndexed { index, tabModel -> Log.d(TAG, "tabModel[$index, ${tabModel.tag}]") }
    }

    private fun getTabEditFragment() = parentFragment as TabEditFragment
}

class TabReplaceViewAdaptor(private val tabEditor: TabEditor) : RecyclerView.Adapter<TabReplaceItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TabReplaceItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.view_tab_edit_item,
                            parent,
                            false))

    override fun getItemCount() = tabEditor.tmpTabs.size

    override fun onBindViewHolder(holder: TabReplaceItemViewHolder, position: Int) {
        holder.binding.title.text = tabEditor.tmpTabs[position].tag
    }
}

class TabReplaceItemViewHolder(val binding: ViewTabEditItemBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.check.visibility = View.GONE
    }
}
