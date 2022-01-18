package com.tsu.hitselka

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.tsu.hitselka.databinding.ControlsBinding
import android.view.ViewGroup
import com.tsu.hitselka.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val controls by lazy { ControlsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val surface = SurfaceView(this)
        setContentView(surface)

        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val background = LayoutInflater.from(this).inflate(R.layout.activity_game, null, false)
        addContentView(background, lp)

        val controlsOverlay = LayoutInflater.from(this).inflate(R.layout.controls, null, false)
        addContentView(controlsOverlay, lp)
    }
}