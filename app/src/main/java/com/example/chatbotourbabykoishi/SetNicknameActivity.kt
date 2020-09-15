package com.example.chatbotourbabykoishi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setnickname.*

class SetNicknameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setnickname)

        enterChatbotButton.setOnClickListener {
            if (enterName.text.toString().isNotEmpty()) {
                if (enterName.text.toString() == "Koishi" || enterName.text.toString() == "Neutral") {
                    // Not Authorized.
                }
                else {
                    PersonalInfo.id = enterName.text.toString()
                    val chatIntent = Intent(this, ChatActivity::class.java)
                    startActivity(chatIntent)
                    finish()
                }
            }
        }
    }
}