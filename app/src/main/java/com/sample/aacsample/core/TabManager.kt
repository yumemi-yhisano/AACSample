package com.sample.aacsample.core

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.sample.aacsample.data.PrefKey
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.ext.getPrefVal
import com.sample.aacsample.ext.putPrefVal

class TabManager(private val context: Context) {
    val tabs = MutableLiveData<List<TabModel<*>>>()

    private val defaultTabsStr = Category.values().joinToString(separator = "|").plus("|clipped")

    init {
        loadTabs()
    }

    fun loadTabs() {
        mutableListOf<TabModel<*>>().let { tmpTabs ->
            context.getPrefVal<String>(PrefKey.TABS).let { tabStr ->
                when {
                    tabStr.isEmpty() -> defaultTabsStr
                    else -> tabStr
                }.split("|").mapTo(tmpTabs) { tag -> createTab(tag) }
            }
            tabs.postValue(tmpTabs)
        }
    }

    fun createTab(tag: String) =
            when (tag) {
                "clipped" -> TabModelImpl(tag, TabMode.FIX_LAST)
                else -> CategorizedTabModelImpl(
                        tag,
                        when (tag) {
                            "general" -> TabMode.FIX_START
                            else -> TabMode.FLOAT
                        }
                )
            }

    fun indexOf(tag: String) = tabs.value?.indexOfFirst { it.tag == tag } ?: 0

    fun saveTabs(tmpTabs: List<TabModel<*>>) {
        tmpTabs.mapTo(mutableListOf()) { it.tag }.joinToString(separator = "|").let {
            context.putPrefVal(PrefKey.TABS, it)
        }
    }

    fun defaultTabTags() = defaultTabsStr.split("|")

    fun isEditable(tag: String) =
            when (tag) {
                "clipped", "general" -> false
                else -> true
            }
}

data class TabModelImpl(override val tag: String, override val tabMode: TabMode)
    : TabModel<TabModelImpl> {
    override fun deepCopy() = copy()
}

data class CategorizedTabModelImpl(override val tag: String, override val tabMode: TabMode)
    : TabModel<CategorizedTabModelImpl>, CategorizedTab {
    override fun deepCopy() = copy()

    override fun getCategory() = Category.valueOf(tag)
}

interface CategorizedTab {
    fun getCategory(): Category
}

interface TabModel<T> {
    val tag: String
    val tabMode: TabMode
    fun deepCopy(): T
}

enum class TabMode {
    FIX_START,
    FIX_LAST,
    FLOAT,
}
