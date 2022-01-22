package com.tsu.hitselka.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object SharedPrefs {
    private const val PREFERENCES = "HitsElka"
    private const val UID_KEY = "UID_KEY"
    private var preferences: SharedPreferences? = null

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
    }

    fun saveUID(uid: String) {
        val instance = preferences ?: return
        with (instance.edit()) {
            putString(UID_KEY, uid)
            apply()
        }
    }

    fun getUID(): String {
        val instance = preferences ?: return ""
        var uid = instance.getString(UID_KEY, "")
        if (uid == null) {
            uid = Firebase.auth.currentUser?.uid ?: return ""
            saveUID(uid)
        }
        return "hIbnn9oQcAhwhgMlkZIyX65RCot2"
    }
}