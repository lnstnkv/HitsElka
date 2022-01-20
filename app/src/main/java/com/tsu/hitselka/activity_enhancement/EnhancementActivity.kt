package com.tsu.hitselka.activity_enhancement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityEnhancementBinding
import com.tsu.hitselka.model.setFullscreen

class EnhancementActivity : AppCompatActivity(R.layout.activity_enhancement) {
    private val binding by lazy { ActivityEnhancementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullscreen()

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}