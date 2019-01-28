package com.sample.aacsample.data.entity

import com.sample.aacsample.ui.base.Diffable
import java.util.UUID

data class ChatItem(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val text: String,
    val player: String
): Diffable {
    val playerType = if (player.isBlank()) PlayerType.OWNER else PlayerType.OTHER
}

enum class PlayerType {
    OWNER,
    OTHER,
}
