package com.sample.aacsample.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

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
