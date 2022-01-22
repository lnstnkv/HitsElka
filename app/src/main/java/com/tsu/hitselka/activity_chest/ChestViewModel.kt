package com.tsu.hitselka.activity_chest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.hitselka.R
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.GiftInfo
import com.tsu.hitselka.model.Inventory
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.min
import kotlin.random.Random

class ChestViewModel : ViewModel() {
    private val _reward = MutableLiveData<List<Inventory>>()
    val reward: LiveData<List<Inventory>> get() = _reward

    fun openChest(time: Long?) {
        val timeWent = if (time == null) {
            1
        } else {
            val now = System.currentTimeMillis().toInt()
            (now - time.toInt()) / (24 * 60 * 60 * 1000)
        }

        val wands = 1000
        val moneys = 200
        val rubies = 50

        val reward = mutableListOf<Inventory>()
        reward.add(Inventory(R.drawable.wand_label, wands * timeWent))
        reward.add(Inventory(R.drawable.money_label, moneys * timeWent))
        reward.add(Inventory(R.drawable.ruby_label, rubies * timeWent))
        _reward.value = reward

        GameLogic.chestOpened(reward)
    }
}