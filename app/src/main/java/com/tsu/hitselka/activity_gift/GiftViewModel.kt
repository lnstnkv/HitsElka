package com.tsu.hitselka.activity_gift

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.hitselka.R
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.GiftInfo
import com.tsu.hitselka.model.Inventory
import kotlin.math.min
import kotlin.random.Random

class GiftViewModel : ViewModel() {
    private val _reward = MutableLiveData<List<Inventory>>()
    val reward: LiveData<List<Inventory>> get() = _reward

    fun openGifts(gift: GiftInfo) {
        val type = gift.gift.type

        val maxGiftCount = when (type) {
            "special" -> 10
            "fairytale" -> 2
            else -> 100
        }
        val giftsToOpen = min(gift.gift.gifts, maxGiftCount - gift.gift.giftsOpened)

        val reward = mutableListOf<Inventory>()
        reward.add(Inventory(R.drawable.wand_label, 0))
        reward.add(Inventory(R.drawable.money_label, 0))
        if (type == "bright") {
            reward.add(Inventory(R.drawable.tokens, 0))
        } else {
            reward.add(Inventory(R.drawable.ruby_label, 0))
        }

        for (i in 0 until giftsToOpen) {
            reward[0].count += Random.nextInt(gift.minFirstReward, gift.maxFirstReward)
            reward[1].count += Random.nextInt(gift.minSecondReward, gift.maxSecondReward)
            reward[2].count += Random.nextInt(gift.minThirdReward, gift.maxThirdReward)
        }
        _reward.value = reward

        GameLogic.giftOpened(gift.gift, giftsToOpen, reward)
    }
}