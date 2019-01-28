package com.sample.aacsample.di

import android.arch.persistence.room.Room
import android.content.Context
import com.sample.aacsample.core.TabManager
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.data.api.repository.ChatRepository
import com.sample.aacsample.data.api.repository.NewsRepository
import com.sample.aacsample.data.api.service.ChatService
import com.sample.aacsample.data.api.service.NewsService
import com.sample.aacsample.data.db.AppDb
import com.sample.aacsample.ui.viewmodel.ChatViewModel
import com.sample.aacsample.ui.viewmodel.ClippedNewsViewModel
import com.sample.aacsample.ui.viewmodel.DetailViewModel
import com.sample.aacsample.ui.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val uiModule = module {
    single { TabManager(androidContext()) }

    viewModel { ClippedNewsViewModel(get()) }

    viewModel { DetailViewModel() }

    viewModel { (category: Category) -> NewsViewModel(get(), get(), category) }

    viewModel { ChatViewModel(get()) }
}

val dataModule = module {
    single { NewsRepository(newsService(okHttpClient())) }

    single { ChatRepository(chatService(okHttpClient())) }

    single { appDb(androidContext()) }
}

fun newsService(client: OkHttpClient): NewsService =
        Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NewsService::class.java)

fun chatService(client: OkHttpClient): ChatService =
        Retrofit.Builder()
                .baseUrl("https://api.a3rt.recruit-tech.co.jp/talk/v1/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ChatService::class.java)
fun okHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

fun appDb(context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "AppDb").build()

val mainApp = listOf(uiModule, dataModule)
