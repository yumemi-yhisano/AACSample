package com.sample.aacsample.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by y_hisano on 2018/08/28.
 */
@Dao
interface ClippedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ClippedArticle)

    @Query("select * from ClippedArticle")
    fun findAll(): LiveData<List<ClippedArticle>>

    @Delete
    fun delete(article: ClippedArticle)
}