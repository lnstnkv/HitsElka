package com.tsu.hitselka

import android.media.MediaPlayer
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.databinding.ActivityGameBinding
import com.tsu.hitselka.databinding.ControlsBinding

class GameActivity : AppCompatActivity(R.layout.controls) {
    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val controls by lazy { ControlsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GameViewModel>()
    private lateinit var clickSound: MediaPlayer
    private lateinit var backgroundMusic: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val surface = SurfaceView(this)
        setContentView(surface)

        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addContentView(binding.root, lp)
        addContentView(controls.root, lp)

        initListeners()
        initObservers()
        setProfileImage()
        hideSystemBars()
        setMusic()
    }

    private fun initListeners() {
        controls.yearSelectorImageView.setOnClickListener {
            Toast.makeText(this, "Year selector", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.achievementsImageView.setOnClickListener {
            Toast.makeText(this, "Achievements", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.giftImageView.setOnClickListener {
            Toast.makeText(this, "Gifts", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.wandView.setOnClickListener {
            Toast.makeText(this, "Enhance", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.settingsImageView.setOnClickListener {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.shopImageView.setOnClickListener {
            Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show()
            playClickSound()
        }

        controls.playImageView.setOnClickListener {
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show()
            playClickSound()
        }
    }

    private fun initObservers() {
        viewModel.resources.observe(this) { resources ->
            controls.wandTextView.text = resources.wands.toString()
            controls.moneyTextView.text = resources.moneys.toString()
            controls.rubyTextView.text = resources.rubies.toString()
        }

        viewModel.stats.observe(this) { stats ->
            controls.levelTextView.text = stats.currentLevel.toString()
        }
    }

    private fun setProfileImage() {
        controls.profileImageView.clipToOutline = true
        val user = Firebase.auth.currentUser

        Glide.with(this)
            .load(user?.photoUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(controls.profileImageView)
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun setMusic() {
        clickSound = MediaPlayer.create(this, R.raw.click)

        backgroundMusic = MediaPlayer.create(this, R.raw.music)
        backgroundMusic.isLooping = true
        backgroundMusic.setVolume(50f, 50f)
        //backgroundMusic.start()
    }

    private fun playClickSound() {
        clickSound.start()
    }

    override fun onRestart() {
        backgroundMusic.start()
        super.onRestart()
    }

    override fun onStop() {
        backgroundMusic.pause()
        super.onStop()
    }

    override fun onDestroy() {
        backgroundMusic.stop()
        backgroundMusic.release()
        clickSound.release()
        super.onDestroy()
    }
}