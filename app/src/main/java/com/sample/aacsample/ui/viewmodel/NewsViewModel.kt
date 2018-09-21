package com.sample.aacsample.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.sample.aacsample.data.api.model.Article
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.data.api.repository.Country
import com.sample.aacsample.data.api.repository.NewsRepository
import com.sample.aacsample.data.db.AppDb
import com.sample.aacsample.data.db.from
import java.util.concurrent.Executors

/**
 * Created by y_hisano on 2018/08/10.
 */
class NewsViewModel(application: Application, private val category: Category) : ViewModel() {

    private val dao = AppDb.get(application).clippedArticleDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getObservableObject() = NewsRepository.getInstance().getData(category)

    fun update() = NewsRepository.getInstance().requestHeadline(Country.jp, category)

    fun clipArticle(article: Article) {
        executor.execute { dao.insert(from(article)) }
    }
}