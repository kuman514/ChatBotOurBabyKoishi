package com.example.chatbotourbabykoishi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

class TitleActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            Log.d(MainActivity.TAG, "Initiation interrupted")
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}