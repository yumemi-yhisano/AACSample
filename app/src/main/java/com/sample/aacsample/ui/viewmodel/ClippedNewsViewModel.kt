package com.sample.aacsample.ui.viewmodel

import android.arch.lifecycle.ViewModel
import com.sample.aacsample.data.db.AppDb
import com.sample.aacsample.data.db.ClippedArticle
import java.util.concurrent.Executors

/**
 * Created by y_hisano on 2018/08/28.
 */
class ClippedNewsViewModel(appDb: AppDb) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()
    private val dao = appDb.clippedArticleDao()

    fun getObservableObject() = dao.findAll()

    fun unclipArticle(article: ClippedArticle) {
        executor.execute{ dao.delete(article) }
    }
}