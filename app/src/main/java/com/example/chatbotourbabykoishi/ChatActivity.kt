package com.example.chatbotourbabykoishi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
        sendBtn!!.setOnClickListener {
            clickSend()
        }
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
                    it.time
                }

                for (i in 0 until msgData.count()) {
                    Log.d(MainActivity.TAG + "ChatActivity", "${msgData[i].time}")
                }
            }
        }

        chatRef!!.addValueEventListener(msgEventListener)
        return msgData
    }

    private fun clickSend() {
        val uid: String = PersonalInfo.id!!
        val cont: String = contentText!!.text.toString()
        val time: Long = System.currentTimeMillis()

        val msgToSend = ChatData(time, uid, cont)
        chatRef!!.push().setValue(msgToSend)
        contentText!!.text.clear()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}