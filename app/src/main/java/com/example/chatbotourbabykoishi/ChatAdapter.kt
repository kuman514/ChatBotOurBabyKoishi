package com.example.chatbotourbabykoishi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: Context, var listData : ArrayList<ChatData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            2 -> {
                ChatKoishiViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.rvitem_chattingmessage, parent, false))
            }
            1 -> {
                ChatMyViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.rvitem_chattingmessage_me, parent, false))
            }
            else -> {
                ChatNeutralViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.rvitem_chattingmessage_neutral, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = listData[position]
        when (holder) {
            is ChatKoishiViewHolder -> {
                holder.setChattingRoom(msg)
            }
            is ChatMyViewHolder -> {
                holder.setChattingRoom(msg)
            }
            is ChatNeutralViewHolder -> {
                holder.setChattingRoom(msg)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(listData[position].sender) {
            PersonalInfo.id -> {
                1
            }
            "Koishi" -> {
                2
            }
            else -> {
                3
            }
        }
    }
}
