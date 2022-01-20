package com.tsu.hitselka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.databinding.ActivitySettingsBinding
import com.tsu.hitselka.model.*
import java.util.*

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val uid by lazy { SharedPrefs.getUID() }
    private val db = Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val database: DatabaseReference = db.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen()

        initButtons()
        initListeners()

        setContentView(binding.root)
    }

    private fun initButtons() {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("players").child(uid).child("settings")
                if (data.value == null) {
                    return
                } else {
                    val settings = data.getValue<Settings>()

                    binding.langToggle.isChecked = settings?.lang ?: true
                    binding.musicToggle.isChecked = settings?.music ?: true
                    binding.soundToggle.isChecked = settings?.sound ?: true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(listener)
    }

    private fun initListeners() {
        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.musicToggle.setOnCheckedChangeListener { _, isChecked ->
            database.child("players").child(uid).child("settings").child("music").setValue(isChecked)
        }

        binding.soundToggle.setOnCheckedChangeListener { _, isChecked ->
            database.child("players").child(uid).child("settings").child("sound").setValue(isChecked)
        }

        binding.langToggle.setOnCheckedChangeListener { _, isChecked ->
            changeLanguage(isChecked)
        }
    }

    private fun changeLanguage(isChecked: Boolean) {
        val locale = if (isChecked) Locale("ru", "RU") else Locale.ENGLISH
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        database.child("players").child(uid).child("settings").child("lang").setValue(isChecked)
    }
}