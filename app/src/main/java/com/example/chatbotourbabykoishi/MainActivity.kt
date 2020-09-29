package com.example.chatbotourbabykoishi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OurBabyKoishiDebug"
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
