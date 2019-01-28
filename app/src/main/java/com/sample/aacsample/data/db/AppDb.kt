package com.sample.aacsample.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sample.aacsample.data.entity.ChatItem

/**
 * Created by y_hisano on 2018/08/28.
 */
@Database(entities = [ClippedArticle::class, ChatItem::class], version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun clippedArticleDao(): ClippedArticleDao
    abstract fun chatItemDao(): ChatItemDao
}
