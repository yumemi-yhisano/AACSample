package com.sample.aacsample.di

import com.sample.aacsample.core.TabManager
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.ui.TransitionManager
import com.sample.aacsample.ui.activity.BaseActivity
import com.sample.aacsample.ui.viewmodel.ClippedNewsViewModel
import com.sample.aacsample.ui.viewmodel.DetailViewModel
import com.sample.aacsample.ui.viewmodel.NewsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { TabManager(get()) }

    viewModel { ClippedNewsViewModel(get()) }

    viewModel { DetailViewModel() }

    viewModel { (category: Category) -> NewsViewModel(get(), category) }

    factory { (activity: BaseActivity) -> TransitionManager(activity) }
}

val mainApp = listOf(appModule)