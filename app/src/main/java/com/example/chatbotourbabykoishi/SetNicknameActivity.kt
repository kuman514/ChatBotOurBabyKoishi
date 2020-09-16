package com.example.chatbotourbabykoishi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setnickname.*

class SetNicknameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setnickname)

        enterChatbotButton.setOnClickListener {
            if (enterName.text.toString().isNotEmpty()) {
                if (enterName.text.toString() == "Koishi" || enterName.text.toString() == "Neutral") {
                    Toast.makeText(applicationContext, "그 이름은 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    PersonalInfo.id = enterName.text.toString()
                    val chatIntent = Intent(this, ChatActivity::class.java)
                    startActivity(chatIntent)
                    finish()
                }
            }
            else {
                Toast.makeText(applicationContext, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}