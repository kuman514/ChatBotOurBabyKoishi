package com.example.chatbotourbabykoishi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        KoishiStatus.initStatus(firebaseDatabase!!, this)

        adapter = ChatAdapter(this, messageItems)
        // [DONE Get references]

        // [Setup the references]
        listView!!.adapter = this.adapter
        listView!!.layoutManager = LinearLayoutManager(this)
        listView!!.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                listView!!.postDelayed( {
                    if(messageItems.size != 0) {
                        listView!!.scrollToPosition(messageItems.size - 1)
                    }
                }, 100)
            }
        }

        sendBtn!!.setOnClickListener {
            if (contentText!!.text.toString().isNotEmpty()) {
                val msgToKoishi = contentText!!.text.toString()
                clickSend()
                GlobalScope.async {
                    koishiResponse(msgToKoishi)
                    if(messageItems.size != 0) {
                        listView!!.scrollToPosition(messageItems.size - 1)
                    }
                }
                contentText!!.text.clear()
            }
        }
        // [DONE Setup the references]

        readMyMessages()
    }

    private fun readMyMessages() {
        val msgEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                messageItems.clear()
                for (msgSnapshot in snapshot.children) {
                    //Log.d(MainActivity.TAG + "Snapshot", msgSnapshot.toString())
                    messageItems.add(
                        ChatData(
                            msgSnapshot.child("time").value as Long,
                            msgSnapshot.child("sender").value as String,
                            msgSnapshot.child("content").value as String
                        )
                    )
                }

                messageItems.sortBy {
                    it.time
                }

                adapter!!.notifyDataSetChanged()
                if (messageItems.size != 0) {
                    listView!!.scrollToPosition(messageItems.size - 1)
                }
            }
        }

        chatRef!!.addValueEventListener(msgEventListener)
    }

    fun showKoishiStatus() {
        fatiguePoint.text = KoishiStatus.fatigue.toString()
        favorabilityPoint.text = KoishiStatus.favorability.toString()
        fullnessPoint.text = KoishiStatus.fullness.toString()
        happinessPoint.text = KoishiStatus.happiness.toString()
    }

    private fun clickSend() {
        val uid: String = PersonalInfo.id!!
        val cont: String = contentText!!.text.toString()
        val time: Long = System.currentTimeMillis()

        val msgToSend = ChatData(time, uid, cont)
        chatRef!!.push().setValue(msgToSend)

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun koishiResponse(msg: String) {
        // Get Dialogflow intent
        val result = KoishiChatBot.talkToKoishi(123, msg)

        // Koishi's message
        val responseMsg = result.fulfillmentText
        //Log.d(MainActivity.TAG + "ChatBotResponse", responseMsg)

        val uid = getString(R.string.koishiChattingId)
        val time: Long = System.currentTimeMillis()

        val msgToSend = ChatData(time, uid, responseMsg)
        chatRef!!.push().setValue(msgToSend)

        // Neutral message
        neutralMessage(result.intent.displayName)
    }

    private fun neutralMessage(displayName: String) {
        //Log.d(MainActivity.TAG + "ChatBotResponseType", displayName)
        when (displayName) {
            "FeelingBerigoo" -> onFeelingBerigoo()
            "GettingAsleep" -> onGettingAsleep()
            "HavingMeal" -> onHavingMeal()
            "RelaxedByPat" -> onRelaxedByPat()
            "SayingHello" -> onSayingHello()
            else -> onDidNotUnderstand()
        }

        showKoishiStatus()
    }

    private fun onFeelingBerigoo() {
        val msg = getString(R.string.chatbotFeelingBerigoo)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)

        KoishiStatus.modifyValue(1, 2, -1, 4)
        KoishiStatus.updateValue()
    }

    private fun onGettingAsleep() {
        val msg = getString(R.string.chatbotGettingAsleep)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)

        KoishiStatus.modifyValue(-50, 0, -10, 5)
        KoishiStatus.updateValue()
    }

    private fun onHavingMeal() {
        val msg = getString(R.string.chatbotHavingMeal)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)

        KoishiStatus.modifyValue(2, 2, 50, 5)
        KoishiStatus.updateValue()
    }

    private fun onRelaxedByPat() {
        val msg = getString(R.string.chatbotRelaxedByPat)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)

        KoishiStatus.modifyValue(1, 5, -1, 5)
        KoishiStatus.updateValue()
    }

    private fun onSayingHello() {
        val msg = getString(R.string.chatbotSayingHello)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)

        KoishiStatus.modifyValue(2, 1, -2, 1)
        KoishiStatus.updateValue()
    }

    private fun onDidNotUnderstand() {
        val msg = getString(R.string.chatbotDidNotUnderstand)
        val uid = getString(R.string.neutralMessageId)
        val time: Long = System.currentTimeMillis()
        val msgToSend = ChatData(time, uid, msg)
        chatRef!!.push().setValue(msgToSend)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // how to kill app process completely
        moveTaskToBack(true)
        finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}