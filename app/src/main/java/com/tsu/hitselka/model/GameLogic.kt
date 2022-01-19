package com.tsu.hitselka.model

import android.content.res.Resources.getSystem
import androidx.core.os.ConfigurationCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

object GameLogic {
    private val db = Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val database: DatabaseReference = db.reference

    fun firstRun(uid: String): PlayerInfo {
        val playerInfo = PlayerInfo()
        val currentLocale = Locale.getDefault().language
        if (currentLocale != "ru") {
            playerInfo.settings.lang = false
        }
        database.child("players").child(uid).setValue(playerInfo)
        return playerInfo
    }
}