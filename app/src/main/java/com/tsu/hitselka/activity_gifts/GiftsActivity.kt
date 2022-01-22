package com.tsu.hitselka.activity_gifts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_gift.GiftActivity
import com.tsu.hitselka.activity_gifts.model.GiftsRecycler
import com.tsu.hitselka.databinding.ActivityGiftsBinding
import com.tsu.hitselka.model.GameData
import com.tsu.hitselka.model.Gift
import com.tsu.hitselka.model.GiftInfo
import com.tsu.hitselka.model.setFullscreen

class GiftsActivity : AppCompatActivity(R.layout.activity_gifts) {
    private val binding by lazy { ActivityGiftsBinding.inflate(layoutInflater) }

    private val giftOpenListener = object : GiftsRecycler.OpenClickListener {
        override fun onOpenClick(gift: GiftInfo) {
            val intent = Intent(this@GiftsActivity, GiftActivity::class.java)
            intent.putExtra("Gift", gift)
            startActivity(intent)
        }
    }
    private val adapter = GiftsRecycler(this, giftOpenListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()
        initObservers()
        initRecyclerView()
        setFullscreen()
    }

    private fun initListeners() {
        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun initObservers() {
        val gifts = mutableListOf<GiftInfo>()
        GameData.gifts.observe(this) {
            gifts.add(getGiftInfo(it.bright))
            gifts.add(getGiftInfo(it.special))
            gifts.add(getGiftInfo(it.fairytale))
            adapter.submitList(gifts)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun getGiftInfo(gift: Gift): GiftInfo {

        val minFirstReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 60
            "special" -> gift.level * gift.level * 150
            else -> gift.level * gift.level * 400
        }
        val maxFirstReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 90
            "special" -> gift.level * gift.level * 200
            else -> gift.level * gift.level * 500
        }

        val minSecondReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 30
            "special" -> gift.level * gift.level * 60
            else -> gift.level * gift.level * 90
        }
        val maxSecondReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 40
            "special" -> gift.level * gift.level * 85
            else -> gift.level * gift.level * 135
        }

        val minThirdReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 12
            "special" -> gift.level * gift.level * 8
            else -> gift.level * gift.level * 22
        }
        val maxThirdReward = when (gift.type) {
            "bright" -> gift.level * gift.level * 17
            "special" -> gift.level * gift.level * 12
            else -> gift.level * gift.level * 30
        }

        return GiftInfo(
            gift,
            minFirstReward,
            maxFirstReward,
            minSecondReward,
            maxSecondReward,
            minThirdReward,
            maxThirdReward
        )
    }
}