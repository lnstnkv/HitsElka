package com.tsu.hitselka.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object GameLogic {
    private val db = Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val database: DatabaseReference = db.reference

    fun firstRun(uid: String): PlayerInfo {
        val playerInfo = PlayerInfo()
        database.child("players").child(uid).setValue(playerInfo)
        return playerInfo
    }
}