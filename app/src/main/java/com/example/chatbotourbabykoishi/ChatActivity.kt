package com.example.chatbotourbabykoishi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {

    var contentText: EditText? = null
    var listView: RecyclerView? = null
    var sendBtn: Button? = null

    var firebaseDatabase: FirebaseDatabase? = null
    var chatRef: DatabaseReference? = null

    var messageItems: ArrayList<ChatData> = ArrayList()
    var adapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        init()
    }

    private fun init() {
        // [Get references]
        contentText = findViewById<EditText>(R.id.chatContent)
        listView = findViewById<RecyclerView>(R.id.recyclerView)
        sendBtn = findViewById<Button>(R.id.sendButton)

        firebaseDatabase = Firebase.database
        chatRef = firebaseDatabase!!.getReference("messages").child(PersonalInfo.id!!)

        messageItems = readMyMessages()
        adapter = ChatAdapter(this, messageItems)
        // [DONE Get references]

        // [Setup the references]

        // [DONE Setup the references]
    }

    private fun readMyMessages() : ArrayList<ChatData> {
        val msgData = ArrayList<ChatData>()
        val msgEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                msgData.clear()
                for (msgSnapshot in snapshot.children) {
                    Log.d(MainActivity.TAG + "Snapshot", msgSnapshot.toString())
                    msgData.add(
                        ChatData(
                            msgSnapshot.child("time").value as Long,
                            msgSnapshot.child("sender").value as String,
                            msgSnapshot.child("content").value as String
                        )
                    )
                }

                msgData.sortBy {
                    it.chatTime
                }

                for (i in 0 until msgData.count()) {
                    Log.d(MainActivity.TAG + "ChatActivity", "${msgData[i].chatTime}")
                }
            }
        }

        chatRef!!.addValueEventListener(msgEventListener)
        return msgData
    }
}