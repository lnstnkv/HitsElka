package com.tsu.hitselka.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PlayerInfo(
    val stats: Stats = Stats(),
    val resources: Resources = Resources(),
    val firstYearStats: FirstYearStats = FirstYearStats(),
    val firstYearInventory: List<Toy> = listOf(),
    val settings: Settings = Settings()
)

data class Stats(
    val currentLevel: Long = 1,
    val currentLevelWandsUsed: Long = 0,
    val objectsBuilt: Long = 0,
    val wandsUsed: Long = 0,
)

data class Resources(
    val wands: Long = 500,
    val rubies: Long = 50,
    val moneys: Long = 50,
)

data class FirstYearStats(
    val secondBuildingLevel: Int = 1,
    val secondBuildingWandsUsed: Int = 0,
    val hedgehogLevel: Int = 1,
    val hedgehogWandsUsed: Int = 0,
    val fatherFrostLevel: Int = 1,
    val fatherFrostWandsUsed: Int = 0,
    val maidenLevel: Int = 1,
    val maidenWandsUsed: Int = 0,
    val forestLevel: Int = 1,
    val forestWandsUsed: Int = 0
)

data class Toy(
    val name: String,
    var x: Int,
    var y: Int
)

data class Settings(
    var lang: Boolean = true, // true - Rus, false - Eng
    val music: Boolean = true,
    val sound: Boolean = true,
)

data class Gift(
    val type: String,
    val level: Int,
    val giftsOpened: Int,
    val gifts: Int,
)

@Parcelize
data class Object(
    val type: String,
    val name: Int,
    val image: Int,
    val level: Long,
    val maxLevel: Long,
    val wandsSpent: Long,
    val wandsNeeded: Long,
    val built: Boolean,
    val locked: Boolean
) : Parcelable
