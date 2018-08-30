package com.sample.aacsample.data.api.model

import android.databinding.BaseObservable

/**
 * Created by y_hisano on 2018/07/27.
 */
data class Headlines (
        val status: String,
        val totalResults: Int,
        val articles: List<Article>
)

data class Article (
        val source: Source,
        val author: String = "",
        val title: String = "",
        val description: String = "",
        val url: String = "",
        val urlToImage: String = "",
        val publishedAt: String = ""
) : BaseObservable()

data class Source (
        val id: String = "",
        val name: String = ""
)