package com.tsu.hitselka.activity_gift

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_gift.model.RewardAdapter
import com.tsu.hitselka.databinding.ActivityGiftBinding
import com.tsu.hitselka.model.GameData
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.GiftInfo
import com.tsu.hitselka.model.setFullscreen

class GiftActivity : AppCompatActivity(R.layout.activity_gift) {
    private val binding by lazy { ActivityGiftBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GiftViewModel>()
    private val adapter = RewardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent
        val gift = intent.getParcelableExtra<GiftInfo>("Gift")
        if (gift == null) {
            finish()
            return
        }

        val image = when (gift.gift.type) {
            "special" -> R.drawable.special_gift
            "fairytale" -> R.drawable.fairytale_gift
            else -> R.drawable.bright_gift
        }
        binding.giftImageView.setImageResource(image)

        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.openButton.setOnClickListener {
            viewModel.openGifts(gift)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.reward.observe(this) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.rewardTextView.visibility = View.VISIBLE
            binding.giftImageView.visibility = View.INVISIBLE
            binding.openButton.visibility = View.INVISIBLE
            adapter.submitList(it)
        }

        setFullscreen()
    }
}