package com.example.chatbotourbabykoishi

import com.google.firebase.database.*


object KoishiStatus {
    var fatigue: Long = 0
    var favorability: Long = 50
    var fullness: Long = 50
    var happiness: Long = 25

    var statRef: DatabaseReference? = null

    fun initStatus(firebaseDatabase: FirebaseDatabase, chatActivity: ChatActivity) {
        statRef = firebaseDatabase.getReference("koishistatus").child(PersonalInfo.id!!)
        statRef!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    fatigue = snapshot.child("fatigue").value as Long
                    favorability = snapshot.child("favorability").value as Long
                    fullness = snapshot.child("fullness").value as Long
                    happiness = snapshot.child("happiness").value as Long
                }
                else {
                    updateValue()
                }

                chatActivity.showKoishiStatus()
            }
        })
    }

    fun updateValue() {
        val koishiStatusSender = KoishiStatusData(fatigue, favorability, fullness, happiness)
        statRef!!.setValue(koishiStatusSender)
    }

    fun modifyValue(ft: Int = 0, fv: Int = 0, ful: Int = 0, hp: Int = 0) {
        fatigue += ft
        favorability += fv
        fullness += ful
        happiness += hp

        if (fatigue < 0) {
            fatigue = 0
        }
        else if (fatigue > 100) {
            fatigue = 100
        }

        if (favorability < 0) {
            favorability = 0
        }
        else if (favorability > 100) {
            favorability = 100
        }

        if (fullness < 0) {
            fullness = 0
        }
        else if (fullness > 100) {
            fullness = 100
        }

        if (happiness < 0) {
            happiness = 0
        }
        else if (happiness > 100) {
            happiness = 100
        }
    }
}