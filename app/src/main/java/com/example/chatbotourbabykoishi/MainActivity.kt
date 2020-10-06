package com.example.chatbotourbabykoishi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OurBabyKoishiDebug"
        lateinit var LANG: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNickname()

        // Main activity terminates right after exiting the chat activity
        finish()
    }

    private fun setNickname() {
        val setNameIntent = Intent(this, SetNicknameActivity::class.java)
        startActivity(setNameIntent)
    }
}
