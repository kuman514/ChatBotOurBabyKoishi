package com.example.chatbotourbabykoishi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {

    var contentText: EditText? = null
    var listView: RecyclerView? = null
    var sendBtn: Button? = null

    var adapter: ChatAdapter? = null
    var messageItems: ArrayList<ChatData> = ArrayList()

    var firebaseDatabase: FirebaseDatabase? = null
    var chatRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        init()
        readMyMessages()
    }

    private fun init() {
        contentText = findViewById<EditText>(R.id.chatContent)
        listView = findViewById<RecyclerView>(R.id.recyclerView)
        sendBtn = findViewById<Button>(R.id.sendButton)

        adapter = ChatAdapter(messageItems, layoutInflater)
        //

        firebaseDatabase = Firebase.database
        chatRef = firebaseDatabase!!.getReference("messages").child(PersonalInfo.id!!)
    }

    private fun readMyMessages() {
    }

}