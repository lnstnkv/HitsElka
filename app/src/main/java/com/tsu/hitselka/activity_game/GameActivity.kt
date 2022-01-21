package com.tsu.hitselka.activity_game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.R
import com.tsu.hitselka.SettingsActivity
import com.tsu.hitselka.SurfaceView
import com.tsu.hitselka.activity_gifts.GiftsActivity
import com.tsu.hitselka.databinding.ActivityGameBinding
import com.tsu.hitselka.databinding.ControlsBinding
import com.tsu.hitselka.inventory.InventoryActivity
import com.tsu.hitselka.model.SharedPrefs
import com.tsu.hitselka.model.setFullscreen
import com.tsu.hitselka.activity_record_book.RecordBookActivity
import com.tsu.hitselka.model.GameData
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.shop.ShopActivity
import java.util.*

class GameActivity : AppCompatActivity(R.layout.controls) {
    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val controls by lazy { ControlsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GameViewModel>()
    private lateinit var surface: SurfaceView
    private lateinit var clickSound: MediaPlayer
    private lateinit var backgroundMusic: MediaPlayer

    private var isMusicOn = false
    private var isSoundOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GameLogic.init()

        surface = SurfaceView(this)
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
        setFullscreen()
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
            playClickSound()
            val intent = Intent(this, GiftsActivity::class.java)
            startActivity(intent)
        }

        controls.wandView.setOnClickListener {
            playClickSound()
            val intent = Intent(this, RecordBookActivity::class.java)
            startActivity(intent)
        }

        controls.settingsImageView.setOnClickListener {
            playClickSound()
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        controls.shopImageView.setOnClickListener {
            playClickSound()
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)

        }

        controls.playImageView.setOnClickListener {
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show()
            playClickSound()
        }
        controls.inventoryImageView.setOnClickListener {
            playClickSound()
            val intent=Intent(this,InventoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initObservers() {
        viewModel.isRussian.observe(this) { state ->
            val locale = if (state) Locale("ru", "RU") else Locale.ENGLISH
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        viewModel.isMusicOn.observe(this) { state ->
            isMusicOn = state
            if (state) {
                backgroundMusic.start()
            } else {
                backgroundMusic.pause()
            }
        }

        viewModel.isSoundOn.observe(this) { state ->
            isSoundOn = state
        }

        GameData.stats.observe(this) { stats ->
            controls.levelTextView.text = stats.currentLevel.toString()
        }

        GameData.resources.observe(this) { resources ->
            controls.wandTextView.text = resources.wands.toString()
            controls.moneyTextView.text = resources.moneys.toString()
            controls.rubyTextView.text = resources.rubies.toString()
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

    private fun setMusic() {
        clickSound = MediaPlayer.create(this, R.raw.click)

        backgroundMusic = MediaPlayer.create(this, R.raw.music)
        backgroundMusic.isLooping = true
        backgroundMusic.setVolume(20f, 20f)
        playMusic()
    }

    private fun playMusic() {
        backgroundMusic.start()
        if (!isMusicOn) {
            backgroundMusic.pause()
        }
    }

    private fun playClickSound() {
        if (isSoundOn) {
            clickSound.start()
        }
    }

    override fun onRestart() {
        playMusic()
        super.onRestart()
    }

    override fun onResume() {
        if (SharedPrefs.getUID().isBlank()) {
            finish()
        }

        surface.start()
        super.onResume()
    }

    override fun onPause() {
        surface.stop()
        super.onPause()
    }

    override fun onDestroy() {
        backgroundMusic.stop()
        backgroundMusic.release()
        clickSound.release()
        super.onDestroy()
    }
}