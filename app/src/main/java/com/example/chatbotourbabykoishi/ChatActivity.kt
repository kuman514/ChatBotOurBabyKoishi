package com.example.chatbotourbabykoishi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
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
        // [Initialize Koishi Chatbot]
        KoishiChatBot.initAssistant(this)
        // [DONE Initialize Koishi Chatbot]

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
        listView!!.adapter = this.adapter
        listView!!.layoutManager = LinearLayoutManager(this)
        listView!!.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                listView!!.postDelayed( {
                    if(messageItems.size != 0) {
                        listView!!.smoothScrollToPosition(messageItems.size - 1)
                    }
                }, 100)
            }
        }
        adapter!!.notifyDataSetChanged()

        sendBtn!!.setOnClickListener {
            if (contentText!!.text.toString().isNotEmpty())
            {
                clickSend()
                koishiResponse(contentText!!.text.toString())
                contentText!!.text.clear()
            }
        }
        // [DONE Setup the references]
    }

    private fun readMyMessages() : ArrayList<ChatData> {
        val msgData = ArrayList<ChatData>()
        val msgEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

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
        //contentText!!.text.clear()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

        //koishiResponse(cont)
    }

    private fun koishiResponse(msg: String) {
        // Get Dialogflow intent
        val result = KoishiChatBot.talkToKoishi(123, msg)

        // Koishi's message
        val responseMsg = result.fulfillmentText
        Log.d(MainActivity.TAG + "ChatBotResponse", responseMsg)

        val uid = getString(R.string.koishiChattingId)
        val time: Long = System.currentTimeMillis()

        val msgToSend = ChatData(time, uid, responseMsg)
        chatRef!!.push().setValue(msgToSend)

        // Neutral message
        neutralMessage(result.intent.displayName)

        contentText!!.text.clear()
    }

    private fun neutralMessage(displayName: String) {
        Log.d(MainActivity.TAG + "ChatBotResponseType", displayName)
        when (displayName) {
            "FeelingBerigoo" -> onFeelingBerigoo()
            "GettingAsleep" -> onGettingAsleep()
            "HavingMeal" -> onHavingMeal()
            "RelaxedByPat" -> onRelaxedByPat()
            "SayingHello" -> onSayingHello()
            else -> onDidNotUnderstand()
        }
    }

    private fun onFeelingBerigoo() {
        val msg = getString(R.string.chatbotFeelingBerigoo)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    private fun onGettingAsleep() {
        val msg = getString(R.string.chatbotGettingAsleep)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    private fun onHavingMeal() {
        val msg = getString(R.string.chatbotHavingMeal)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    private fun onRelaxedByPat() {
        val msg = getString(R.string.chatbotRelaxedByPat)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    private fun onSayingHello() {
        val msg = getString(R.string.chatbotSayingHello)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    private fun onDidNotUnderstand() {
        val msg = getString(R.string.chatbotDidNotUnderstand)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }
}