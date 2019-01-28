package com.sample.aacsample.data.api.model

data class Chat(
    val status: Int,
    val message: String,
    val results: List<ChatResult>?
)

data class ChatResult(
    val perplexity: Double,
    val reply: String
)
