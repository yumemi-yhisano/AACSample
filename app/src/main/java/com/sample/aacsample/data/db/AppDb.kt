package com.sample.aacsample.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by y_hisano on 2018/08/28.
 */
@Database(entities = arrayOf(ClippedArticle::class), version = 1, exportSchema = false)
abstract class AppDb: RoomDatabase() {
    abstract fun clippedArticleDao(): ClippedArticleDao
}