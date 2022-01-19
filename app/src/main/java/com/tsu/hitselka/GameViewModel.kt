package com.tsu.hitselka

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
import com.tsu.hitselka.model.Resources
import com.tsu.hitselka.model.SharedPrefs
import com.tsu.hitselka.model.Stats

class GameViewModel : ViewModel() {
    private val db =
        Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val database: DatabaseReference = db.reference

    private val _resources = MutableLiveData<Resources>()
    val resources: LiveData<Resources> get() = _resources

    private val _stats = MutableLiveData<Stats>()
    val stats: LiveData<Stats> get() = _stats

    init {
        val uid = SharedPrefs.getUID()
        setDatabaseListener(uid)
    }

    private fun setDatabaseListener(uid: String) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("players").child(uid)
                if (data.value == null) {
                    GameLogic.firstRun(uid)
                } else {
                    val resources = data.child("resources").getValue<Resources>()
                    _resources.value = resources ?: return

                    val stats = data.child("stats").getValue<Stats>()
                    _stats.value = stats ?: return
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(listener)
    }
}