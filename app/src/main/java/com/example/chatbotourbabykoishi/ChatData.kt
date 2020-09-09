package com.example.chatbotourbabykoishi

import java.io.Serializable

class ChatData : Serializable {
    var uid : String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }

    var context : String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }
}