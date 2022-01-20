package com.tsu.hitselka.activity_gifts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_gifts.model.GiftsRecycler
import com.tsu.hitselka.databinding.ActivityGiftsBinding
import com.tsu.hitselka.model.Gift
import com.tsu.hitselka.model.setFullscreen

class GiftsActivity : AppCompatActivity(R.layout.activity_gifts) {
    private val binding by lazy { ActivityGiftsBinding.inflate(layoutInflater) }

    private val giftOpenListener = object : GiftsRecycler.OpenClickListener {
        override fun onOpenClick(gift: Gift) {
            Toast.makeText(this@GiftsActivity, "Opened ${gift.type} gift", Toast.LENGTH_SHORT).show()
        }
    }
    private val adapter = GiftsRecycler(this, giftOpenListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()
        initRecyclerView()
        setFullscreen()
    }

    private fun initListeners() {
        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter

        val gifts = mutableListOf<Gift>()
        gifts.add(Gift("bright", 10, 35, 43))
        gifts.add(Gift("special", 4, 0, 5))
        gifts.add(Gift("fairytale", 1, 8, 3))

        adapter.submitList(gifts)
    }
}