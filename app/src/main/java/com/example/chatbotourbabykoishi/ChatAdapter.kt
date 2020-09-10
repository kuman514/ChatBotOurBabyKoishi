package com.example.chatbotourbabykoishi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class ChatAdapter(_messageItems : ArrayList<ChatData>, _layoutInflater: LayoutInflater) : BaseAdapter() {
    val chatMessages : ArrayList<ChatData> = _messageItems
    val layoutInflater : LayoutInflater = _layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val chatMsg : ChatData = chatMessages[position]

        val itemView : View = if (chatMsg.uid.equals(PersonalInfo.id)) {
            layoutInflater.inflate(R.layout.rvitem_chattingmessage_me, parent,false)
        } else {
            layoutInflater.inflate(R.layout.rvitem_chattingmessage, parent,false)
        }

        //val profilePic = itemView.findViewById<ImageView>(R.id.chattingMessageProfileImageView)
        val tvName = itemView.findViewById<TextView>(R.id.chattingMessageNameTextView)
        val tvMsg = itemView.findViewById<TextView>(R.id.chattingMessageTextView)
        val tvTime = itemView.findViewById<TextView>(R.id.chattingMessageTimestampTextView)

        tvName.text = chatMsg.uid
        tvMsg.text = chatMsg.content
        tvTime.text = chatMsg.chatTime.toString()

        return itemView
    }

    override fun getItem(position: Int): Any {
        return chatMessages[position]
    }

    override fun getItemId(position: Int): Long {
        return chatMessages[position].chatTime
    }

    override fun getCount(): Int {
        return chatMessages.size
    }
}