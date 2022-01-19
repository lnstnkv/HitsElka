package com.tsu.hitselka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.hitselka.databinding.ActivitySettingsBinding
import com.tsu.hitselka.model.setFullscreen

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFullscreen()
    }
}