package com.tsu.hitselka.activity_enhancement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.Stats
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.log10

class EnhancementViewModel : ViewModel() {
    private var wandsTotal: Long = 0
    private var wandsSpent: Long = 0
    private var wandsNeeded: Long = 0
    private var wandsSpentForLevel: Long = 0
    private lateinit var stats: Stats
    private lateinit var item: Object

    private var wandSpending = false
    private var wandsAvailable: Long = 0
    private var job: Job? = null

    private val _wands = MutableLiveData<Long>()
    val wands: LiveData<Long> get() = _wands

    private val _wandsRemain = MutableLiveData<Long>()
    val wandsRemain: LiveData<Long> get() = _wandsRemain

    private val _objectProgress = MutableLiveData<Long>()
    val objectProgress: LiveData<Long> get() = _objectProgress

    private val _levelProgress = MutableLiveData<Long>()
    val levelProgress: LiveData<Long> get() = _levelProgress

    fun initViewModel(item: Object) {
        this.item = item

        Log.d("MyTag", item.toString())

        wandsTotal = item.wandsNeeded
        wandsNeeded = wandsTotal - (wandsSpent + item.wandsSpent)

        _wandsRemain.value = wandsNeeded
        _objectProgress.value = (wandsSpent + item.wandsSpent) * 100 / wandsTotal
    }

    fun startSpending() {
        wandSpending = true
        job = viewModelScope.launch {
            while (wandSpending) {
                spendWand()
                delay(75L)
            }
        }
    }

    fun stopSpending() {
        wandSpending = false
        job?.cancel()
    }

    fun setAvailableWands(wands: Long) {
        wandsAvailable = wands
        _wands.value = wands
    }

    private var level: Long = 0
    private var levelNeeded: Long = 0
    private var levelWandsSpent: Long = 0

    fun setStats(stats: Stats) {
        this.stats = stats

        level = stats.currentLevel
        levelWandsSpent = stats.currentLevelWandsUsed
        wandsSpentForLevel = 0

        val coefficient = when {
            level % 100 == 99L -> 700
            level % 10 == 9L -> 400
            else -> 100
        }
        levelNeeded =
            floor(log10(level.toDouble() + 1) * coefficient * (level.div(10) + 1)).toLong()
    }

    private fun spendWand() {
        wandsSpent++
        wandsSpentForLevel++
        wandsNeeded--
        wandsAvailable--

        _wandsRemain.value = wandsNeeded
        _objectProgress.value = (item.wandsSpent + wandsSpent) * 100 / wandsTotal
        _levelProgress.value = (wandsSpentForLevel + stats.currentLevelWandsUsed) * 100 / levelNeeded
        _wands.value = wandsAvailable

        if (wandsNeeded == 0L) {
            GameLogic.upgrade(item)
            return
        }

        if (wandsSpentForLevel + stats.currentLevelWandsUsed == levelNeeded) {
            GameLogic.newLevel()
            return
        }

        if (wandsAvailable == 0L) {
            stopSpending()
            return
        }
    }

    fun sendWands() {
        GameLogic.sendWands(item, wandsSpent, wandsSpentForLevel)
    }
}