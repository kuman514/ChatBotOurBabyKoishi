package com.example.chatbotourbabykoishi

import java.io.Serializable

class ChatData(var chatTime: Long, var uid: String, var content: String) : Serializable {
}