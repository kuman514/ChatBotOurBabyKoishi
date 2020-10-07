package com.example.chatbotourbabykoishi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OurBabyKoishiDebug"
        lateinit var LANG: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val systemLocale = applicationContext.resources.configuration.locales[0]
        LANG = when (systemLocale.language) {
            "ko" -> "ko"
            else -> "en"
        }
        Log.d(TAG + "Language", LANG)

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
