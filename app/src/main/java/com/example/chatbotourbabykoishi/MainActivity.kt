package com.example.chatbotourbabykoishi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
        //helloKoishi()
    }

    private fun helloKoishi() {
        // [START write_message]
        // Write a message to the database
        val database = Firebase.database
        val msgSend = database.getReference("messages").child(Calendar.MILLISECOND.toString())

        val msgUid = msgSend.child("uid")
        val msgCont = msgSend.child("content")

        msgUid.setValue("kuman514")
        msgCont.setValue("Hello, Koishi!")
        // [END write_message]

        /*
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
}
