package com.sample.aacsample.ui.viewmodel

import android.arch.lifecycle.ViewModel
import com.sample.aacsample.core.BaseCoroutineScope
import com.sample.aacsample.data.api.ApiResult
import com.sample.aacsample.data.api.repository.ChatRepository
import com.sample.aacsample.data.db.AppDb
import com.sample.aacsample.data.entity.ChatItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

/**
 * Created by y_hisano on 2018/08/15.
 */
class ChatViewModel(private val chatRepository: ChatRepository, private val appDb: AppDb) : ViewModel(), CoroutineScope by BaseCoroutineScope(Dispatchers.Main) {

    private val dao = appDb.chatItemDao()

    val chatItems = dao.findAll()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun requestSmalltalk(message: String) {
        addChatItem(ChatItem(text = message, player = ""))
        launch {
            when (val result = chatRepository.requestSmalltalk(message)) {
                is ApiResult.Success -> {
                    addChatItem(result.data)
                }
            }
        }
    }

    private fun addChatItem(item: ChatItem) {
        launch (Dispatchers.Default) {
            dao.insert(item)
        }
    }
}
