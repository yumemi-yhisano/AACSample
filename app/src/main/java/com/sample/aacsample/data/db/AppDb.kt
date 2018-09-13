package com.sample.aacsample.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by y_hisano on 2018/08/28.
 */
@Database(entities = arrayOf(ClippedArticle::class), version = 1, exportSchema = false)
abstract class AppDb: RoomDatabase() {
    abstract fun clippedArticleDao(): ClippedArticleDao

    companion object {
        private var instance: AppDb? = null

        @Synchronized
        fun get(context: Context) =
                instance ?: Room.databaseBuilder(context.applicationContext, AppDb::class.java, "AppDb").build()
                        .also { instance = it }
    }
}