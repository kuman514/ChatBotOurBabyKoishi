package com.example.chatbotourbabykoishi

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rvitem_chattingmessage.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatKoishiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setChattingRoom(chatMsg: ChatData) {
        Log.d(MainActivity.TAG + "ChatMyViewHolder", itemView.toString())

        val tvMsg = itemView.chattingMessageTextView
        val tvTime = itemView.chattingMessageTimestampTextView

        tvMsg.text = chatMsg.content
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm aa", Locale.KOREA)
        tvTime.text = format.format(chatMsg.time).toString()
    }
}