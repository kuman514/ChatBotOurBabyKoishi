package com.example.chatbotourbabykoishi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: Context, var listData : ArrayList<ChatData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 2) {
            ChatKoishiViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.rvitem_chattingmessage, parent, false))
        }
        else {
            ChatMyViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.rvitem_chattingmessage_me, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = listData[position]
        if (holder is ChatKoishiViewHolder) {
            // Smart cast
            holder.setChattingRoom(msg)
        } else if (holder is ChatMyViewHolder) {
            // Smart cast
            holder.setChattingRoom(msg)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (PersonalInfo.id.equals(listData.get(position).uid)) {
            1
        } else {
            2
        }
    }

}
