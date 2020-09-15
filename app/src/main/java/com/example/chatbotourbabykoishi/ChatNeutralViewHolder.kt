package com.example.chatbotourbabykoishi

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rvitem_chattingmessage_neutral.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatNeutralViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    fun setChattingRoom(chatMsg: ChatData) {
        Log.d(MainActivity.TAG + "ChatMyViewHolder", itemView.toString())

        val tvMsg = itemView.chattingMessageNeutralTextView
        tvMsg.text = chatMsg.content
    }
}