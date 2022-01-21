package com.tsu.hitselka.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object GameData {
    private val db =
        Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val uid = SharedPrefs.getUID()
    private val database: DatabaseReference = db.reference
    private val player = database.child("players").child(uid)
    private val playerStats = player.child("stats")
    private val playerResources = player.child("resources")

    private val _stats = MutableLiveData<Stats>()
    val stats: LiveData<Stats> get() = _stats

    private val _resources = MutableLiveData<Resources>()
    val resources: LiveData<Resources> get() = _resources

    fun init() {
        playerStats.addValueEventListener(statsListener)
        playerResources.addValueEventListener(resourcesListener)
    }

    private val statsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val stats = snapshot.getValue<Stats>()
            _stats.value = stats ?: return
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    private val resourcesListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val resources = snapshot.getValue<Resources>()
            _resources.value = resources ?: return
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    fun getStats(): Stats? {
        return _stats.value
    }

    fun getResources(): Resources? {
        return _resources.value
    }
}