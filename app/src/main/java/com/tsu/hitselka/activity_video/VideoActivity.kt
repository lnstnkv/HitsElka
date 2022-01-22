package com.tsu.hitselka.activity_video

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_game.GameActivity
import com.tsu.hitselka.databinding.ActivityVideoBinding
import com.tsu.hitselka.model.setFullscreen

class VideoActivity : AppCompatActivity(R.layout.activity_video) {
    private val binding by lazy { ActivityVideoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFullscreen()

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        val uri = Uri.parse("android.resource://$packageName/${R.raw.trailer}")

        with (binding.videoView) {
            setMediaController(mediaController)
            setVideoURI(uri)
            requestFocus()
            start()
        }

        binding.closeButton.setOnClickListener {
            binding.videoView.stopPlayback()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}