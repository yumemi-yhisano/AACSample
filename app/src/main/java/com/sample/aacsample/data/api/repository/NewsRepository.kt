package com.sample.aacsample.data.api.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sample.aacsample.BuildConfig
import com.sample.aacsample.data.api.model.Article
import com.sample.aacsample.data.api.model.Headlines
import com.sample.aacsample.data.api.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by y_hisano on 2018/07/27.
 */
class NewsRepository(val service: NewsService) {
    private val articleData =
            Category.values().map { it to MutableLiveData<List<Article>>() }.toMap()

    fun requestHeadline(country: Country, category: Category): LiveData<List<Article>> {
        val data = articleData.getOrElse(category) { MutableLiveData() }
        service.headline(BuildConfig.NEWS_API_KEY, country.name, category.name).enqueue(object : Callback<Headlines> {
            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                data.postValue(null)
            }

            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                data.postValue(response.body()?.articles)
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
