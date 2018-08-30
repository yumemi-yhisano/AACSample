package com.sample.aacsample.data.db

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sample.aacsample.data.api.model.Article

/**
 * Created by y_hisano on 2018/08/28.
 */
@Entity
data class ClippedArticle(
        @Embedded
        val source: ClippedSource,
        val author: String = "",
        val title: String = "",
        val description: String = "",
        @PrimaryKey val url: String = "",
        val urlToImage: String = "",
        val publishedAt: String = ""
)

data class ClippedSource (
        val id: String,
        val name: String
)

fun from (article: Article) = ClippedArticle(
        ClippedSource(article.source.id ?: "", article.source.name ?: ""),
        article.author, article.title, article.description, article.url,
        article.urlToImage, article.publishedAt)