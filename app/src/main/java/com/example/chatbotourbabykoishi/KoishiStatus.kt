package com.example.chatbotourbabykoishi

import com.google.firebase.database.*


object KoishiStatus {
    var fatigue: Int = 0
    var favorability: Int = 50
    var fullness: Int = 50
    var happiness: Int = 25

    var statRef: DatabaseReference? = null

    fun initStatus(firebaseDatabase: FirebaseDatabase) {
        statRef = firebaseDatabase.getReference("koishistatus").child(PersonalInfo.id!!)
        statRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    fatigue = snapshot.child("fatigue").value as Int
                    favorability = snapshot.child("favorability").value as Int
                    fullness = snapshot.child("fullness").value as Int
                    happiness = snapshot.child("happiness").value as Int
                }
                else {
                    // run some code
                    statRef!!.child("fatigue").setValue(fatigue)
                    statRef!!.child("favorability").setValue(favorability)
                    statRef!!.child("fullness").setValue(fullness)
                    statRef!!.child("happiness").setValue(happiness)
                }
            }
        })
    }

    fun setValue() {

    }
}