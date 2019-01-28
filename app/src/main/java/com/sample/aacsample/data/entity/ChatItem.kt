package com.sample.aacsample.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.sample.aacsample.ui.base.Diffable
import java.util.UUID

@Entity
data class ChatItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val text: String,
    val player: String
): Diffable {
    @Ignore
    val playerType = if (player.isBlank()) PlayerType.OWNER else PlayerType.OTHER
}

enum class PlayerType {
    OWNER,
    OTHER,
}
