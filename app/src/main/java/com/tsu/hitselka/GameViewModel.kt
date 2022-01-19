package com.tsu.hitselka

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.PlayerInfo
import com.tsu.hitselka.model.SharedPrefs

class GameViewModel: ViewModel() {
    private val db = Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val database: DatabaseReference = db.reference

    private val _player = MutableLiveData<PlayerInfo>()
    val player: LiveData<PlayerInfo> get() = _player

    init {
        val uid = SharedPrefs.getUID()
        setDatabaseListener(uid)
    }

    private fun setDatabaseListener(uid: String) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("players").child(uid).getValue<PlayerInfo?>()
                if (data == null) {
                    GameLogic.firstRun(uid)
                } else {
                    _player.value = data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(listener)
    }
}