package com.sample.aacsample.data.api.repository

import com.sample.aacsample.BuildConfig
import com.sample.aacsample.data.api.ApiResult
import com.sample.aacsample.data.api.service.ChatService
import com.sample.aacsample.data.entity.ChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by y_hisano on 2018/07/27.
 */
class ChatRepository(val service: ChatService) {
    suspend fun requestSmalltalk(query: String): ApiResult<ChatItem> = withContext(Dispatchers.Default) {
        try {
            val result = service.smalltalk(
                RequestBody.create(MediaType.parse("text/plain"), BuildConfig.A3RT_API_KEY),
                RequestBody.create(MediaType.parse("text/plain"), query)
            ).execute()
            if (! result.isSuccessful) {
                return@withContext ApiResult.Error("")
            }
            if (result.body()?.status == 0) {
                val chat = checkNotNull(result.body()?.results?.getOrNull(0))
                return@withContext ApiResult.Success(ChatItem(
                    text = chat.reply,
                    player = "A3RT"
                ))
            }
        } catch (e: Exception) { }
        return@withContext ApiResult.Error("")
    }
}
