package com.sample.aacsample.ui.base

import com.sample.aacsample.MainApplication
import com.sample.aacsample.core.TabMode
import com.sample.aacsample.core.TabModel
import java.lang.ref.WeakReference

class TabEditor {
    private val tabManager = MainApplication.getInstance().appEnv.tabManager
    val tmpTabs = mutableListOf<TabModel<*>>()
    private val tabChangeObservers = mutableListOf<WeakReference<TmpTabChangeObserver>>()

    fun copyTmpTabs() {
        tmpTabs.clear()
        tabManager.tabs.value?.mapTo(tmpTabs) { it.deepCopy() as TabModel<*> }
    }

    fun saveTmpTabs() {
        tabManager.saveTabs(tmpTabs)
        tabManager.loadTabs()
    }

    fun addTabToTmpTabs(tag: String) {
        when {
            tmpTabs.findLast { tabModel -> tabModel.tabMode == TabMode.FIX_LAST } == null -> tmpTabs.size
            else -> tmpTabs.size - 1
        }.let { index ->
            tmpTabs.add(index, tabManager.createTab(tag))
            tabChangeObservers.forEach { it.get()?.onInsert(index) }
        }
    }

    fun removeTabFromTmpTabs(tag: String) {
        tmpTabs.indexOfFirst { tabModel -> tabModel.tag == tag }.let { index ->
            tmpTabs.removeAt(index)
            tabChangeObservers.forEach { it.get()?.onDelete(index) }
        }
    }

    fun defaultTabTags() = tabManager.defaultTabTags()

    fun isEditable(tag: String) = tabManager.isEditable(tag)

    fun addTmpTabChangeObserver(observer: TmpTabChangeObserver) {
        tabChangeObservers.add(WeakReference(observer))
    }
}

interface TmpTabChangeObserver {
    fun onInsert(position: Int)
    fun onDelete(position: Int)
}