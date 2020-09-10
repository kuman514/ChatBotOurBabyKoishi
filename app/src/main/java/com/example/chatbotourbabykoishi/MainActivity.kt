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
        private const val TAG = "OurBabyKoishiDebug"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloKoishi()
        //showTitle()
        //setNickname()
        enterChatbot()

        // Main activity terminates right after exiting the chat activity
        finish()
    }

    private fun helloKoishi() {
        // For testing
        PersonalInfo.id = "kuman514"

        /*
        // [START write_message]
        // Write a message to the database
        val database = Firebase.database
        val msgSend = database.getReference("messages").child(Calendar.MILLISECOND.toString())

        val msgUid = msgSend.child("uid")
        val msgCont = msgSend.child("content")

        msgUid.setValue(PersonalInfo.id)
        msgCont.setValue("Hello, Koishi!")
        // [END write_message]

        // [START read_message]
        // Read from the database
        msgSend.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        // [END read_message]
        */
    }

    private fun showTitle() {
        TODO("Not implemented yet")
    }

    private fun setNickname() {
        TODO("Not implemented yet")
    }

    private fun enterChatbot() {
        val chatIntent = Intent(this, ChatActivity::class.java)
        startActivity(chatIntent)
    }
}
