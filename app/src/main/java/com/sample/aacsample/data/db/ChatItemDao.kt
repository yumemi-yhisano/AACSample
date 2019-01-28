package com.sample.aacsample.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sample.aacsample.data.entity.ChatItem

/**
 * Created by y_hisano on 2018/08/28.
 */
@Dao
interface ChatItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ChatItem)

    @Query("select * from ChatItem")
    fun findAll(): LiveData<List<ChatItem>>

    @Delete
    fun delete(item: ChatItem)
}
