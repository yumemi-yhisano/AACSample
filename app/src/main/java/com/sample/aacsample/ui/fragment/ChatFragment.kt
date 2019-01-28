package com.sample.aacsample.ui.fragment

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.data.entity.ChatItem
import com.sample.aacsample.data.entity.PlayerType
import com.sample.aacsample.databinding.FragmentChatBinding
import com.sample.aacsample.databinding.ViewChatItemOtherBinding
import com.sample.aacsample.databinding.ViewChatItemOwnerBinding
import com.sample.aacsample.ext.TAG
import com.sample.aacsample.ext.createBinding
import com.sample.aacsample.ui.base.calculateDiff
import com.sample.aacsample.ui.viewmodel.ChatViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.properties.Delegates

/**
 * Created by y_hisano on 2018/07/24.
 */
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModel()
    private lateinit var binding: FragmentChatBinding

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observableViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        binding.toolbar?.title?.text = "チャット"
        initToolbar()

        binding.recyclerView.adapter = ChatRecyclerAdapter()

        binding.btnSend.setOnClickListener {
            val text = binding.chatInput.text.toString()
            if (text.isBlank()) return@setOnClickListener
            binding.chatInput.setText("")
            viewModel.requestSmalltalk(text)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateToolbar()
    }

    private fun observableViewModel(viewModel: ChatViewModel) {
        viewModel.chatItems.observe(this, Observer<List<ChatItem>> { list ->
            if (list != null) {
                Log.d(TAG, "onChanged $list")
                (binding.recyclerView.adapter as? ChatRecyclerAdapter)?.let {
                    it.chatItems = list
                }
            }
        })
    }

    private fun initToolbar() {
        binding.toolbar?.leftArrow?.setOnClickListener {
            val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
            if (fragmentPushed) {
                getActivityFragmentManager().popBackStack()
            }
        }
        binding.toolbar?.menu?.visibility = View.GONE
    }

    private fun updateToolbar() {
        val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
        binding.toolbar?.leftArrow?.visibility = if (fragmentPushed) View.VISIBLE else View.GONE
    }

    private fun getActivityFragmentManager() = activity!!.supportFragmentManager!!
}

private class ChatRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var chatItems: List<ChatItem> by Delegates.observable(emptyList()) { _, old, new ->
        calculateDiff(old, new).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (PlayerType.values().getOrNull(viewType)) {
        PlayerType.OWNER -> ChatItemOwnerViewHolder(createBinding(R.layout.view_chat_item_owner, parent))
        PlayerType.OTHER -> ChatItemOtherViewHolder(createBinding(R.layout.view_chat_item_other, parent))
        else -> throw IllegalArgumentException("viewType[$viewType] is not defined.")
    }

    override fun getItemCount() = chatItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = chatItems[position]
        when (holder) {
            is ChatItemOwnerViewHolder -> {
                holder.binding.text.text = item.text
                holder.binding.time.text = CHAT_TIME_FORMAT.format(item.timestamp)
            }
            is ChatItemOtherViewHolder -> {
                holder.binding.text.text = item.text
                holder.binding.time.text = CHAT_TIME_FORMAT.format(item.timestamp)
            }
        }
    }

    override fun getItemViewType(position: Int) = chatItems[position].playerType.ordinal
}

private class ChatItemOwnerViewHolder(val binding: ViewChatItemOwnerBinding) : RecyclerView.ViewHolder(binding.root)

private class ChatItemOtherViewHolder(val binding: ViewChatItemOtherBinding) : RecyclerView.ViewHolder(binding.root)

private val CHAT_TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.JAPANESE)