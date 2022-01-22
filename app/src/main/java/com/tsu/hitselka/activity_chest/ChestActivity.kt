package com.tsu.hitselka.activity_chest

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_gift.model.RewardAdapter
import com.tsu.hitselka.databinding.ActivityChestBinding
import com.tsu.hitselka.databinding.ActivityGiftBinding
import com.tsu.hitselka.model.GameData
import com.tsu.hitselka.model.GiftInfo
import com.tsu.hitselka.model.setFullscreen

class ChestActivity : AppCompatActivity(R.layout.activity_chest) {
    private val binding by lazy { ActivityChestBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ChestViewModel>()
    private val adapter = RewardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.openButton.setOnClickListener {
            val stats = GameData.getStats() ?: return@setOnClickListener
            viewModel.openChest(stats.chestLastOpened)
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