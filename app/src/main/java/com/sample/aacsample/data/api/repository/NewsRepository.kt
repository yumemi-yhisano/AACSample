package com.sample.aacsample.data.api.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sample.aacsample.data.api.model.Article
import com.sample.aacsample.data.api.model.Headlines
import com.sample.aacsample.data.api.service.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by y_hisano on 2018/07/27.
 */
class NewsRepository private constructor() {
    private val apiKey = "f10141ddd2f842bebece5fabb72c4d7c"
    private val service: NewsService
    private val articleData = Category.values().map { it to MutableLiveData<List<Article>>() } .toMap()

    init {
        val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        service = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NewsService::class.java)
    }

    companion object {
        private var instance: NewsRepository? = null

        fun getInstance() = instance ?: NewsRepository().also { instance = it }
    }

    fun requestHeadline(country: Country, category: Category): LiveData<List<Article>> {
        val data = articleData[category]!!
        service.headline(apiKey, country.name, category.name).enqueue(object: Callback<Headlines> {
            override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                data.postValue(null)
            }

            override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                data.postValue(response?.body()?.articles)
            }
        })
        return data
    }

    fun getData(category: Category) = articleData[category]!!
}

enum class Country {
    jp
}

enum class Category {
    general,
    business,
    entertainment,
    health,
    science,
    sports,
    technology,
}