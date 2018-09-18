package com.sample.aacsample.core

import android.content.Context
import com.sample.aacsample.data.PrefKey
import com.sample.aacsample.data.Preferences
import com.sample.aacsample.data.api.repository.Category

class TabManager(private val context: Context) {
    val tabs = arrayListOf<TabModel>()

    private val defaultTabsStr = Category.values().joinToString(separator = "|").plus("|clipped")

    fun loadTabs() {
        tabs.clear()
        Preferences.getVal(context, PrefKey.TABS, String::class.java).let { tabStr ->
            when {
                tabStr.isEmpty() -> defaultTabsStr
                else -> tabStr
            }.split("|").mapTo(tabs){ tag -> createTab(tag) }
        }
    }

    private fun validateTabsOrder() : Boolean {
        tabs.forEachIndexed { index, tabModel ->
            if (index > 0 && tabModel.tabMode == TabMode.FIX_START) {
                return false
            }
            if (index < tabs.size - 1 && tabModel.tabMode == TabMode.FIX_LAST) {
                return false
            }
        }
        return true
    }

    private fun createTab(tag: String) =
            when(tag) {
                "clipped" -> TabModelImpl(tag, TabMode.FIX_LAST)
                else -> CategorizedTabModelImpl(
                        tag,
                        when (tag) {
                            "general" -> TabMode.FIX_START
                            else -> TabMode.FLOAT
                        }
                )
            }
}

data class TabModelImpl(override val tag: String, override val tabMode: TabMode) : TabModel

data class CategorizedTabModelImpl(override val tag: String, override val tabMode: TabMode) : TabModel, CategorizedTab {
    override fun getCategory() = Category.valueOf(tag)
}

interface CategorizedTab {
    fun getCategory(): Category
}

interface TabModel {
    val tag: String
    val tabMode: TabMode
}
enum class TabMode {
    FIX_START,
    FIX_LAST,
    FLOAT,
}