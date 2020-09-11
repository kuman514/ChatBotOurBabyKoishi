package com.example.chatbotourbabykoishi

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rvitem_chattingmessage.view.*
import kotlinx.android.synthetic.main.rvitem_chattingmessage_me.view.*

class ChatMyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setChattingRoom(chatMsg: ChatData) {
        Log.d(MainActivity.TAG + "ChatMyViewHolder", itemView.toString())

        //val tvName = itemView.chattingMessageNameTextView
        val tvMsg = itemView.chattingMessageMeTextView
        val tvTime = itemView.chattingMessageMeTimestampTextView

        //tvName.text = chatMsg.sender
        tvMsg.text = chatMsg.content
        tvTime.text = chatMsg.time.toString()
    }
}