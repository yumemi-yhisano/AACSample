package com.sample.aacsample.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sample.aacsample.data.api.ApiResult
import com.sample.aacsample.data.api.repository.ChatRepository
import com.sample.aacsample.data.entity.ChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by y_hisano on 2018/08/15.
 */
class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    val chatList = MutableLiveData<List<ChatItem>>()

    fun requestSmalltalk(message: String) {
        addChatItem(ChatItem(text = message, player = ""))
        GlobalScope.launch(Dispatchers.Main) {
            when (val result = chatRepository.requestSmalltalk(message)) {
                is ApiResult.Success -> {
                    addChatItem(result.data)
                }
            }
        }
    }

    private fun addChatItem(item: ChatItem) {
        val list = mutableListOf<ChatItem>()
        chatList.value?.let { list.addAll(it) }
        list.add(item)
        chatList.value = list
    }
}
