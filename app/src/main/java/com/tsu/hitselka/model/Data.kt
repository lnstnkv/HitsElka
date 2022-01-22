package com.tsu.hitselka.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PlayerInfo(
    val stats: Stats = Stats(),
    val resources: Resources = Resources(),
    val firstYearStats: FirstYearStats = FirstYearStats(),
    val firstYearInventory: List<Toy> = listOf(),
    val settings: Settings = Settings(),
    val gifts: Gifts = Gifts(),
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
    val father_frost: BuildingStats = BuildingStats(wandsNeeded = 2500L),
    val forest: BuildingStats = BuildingStats(wandsNeeded = 1300L),
    val hedgehog: BuildingStats = BuildingStats(wandsNeeded = 1600L),
    val maiden: BuildingStats = BuildingStats(wandsNeeded = 2000L),
    val university: BuildingStats = BuildingStats(wandsNeeded = 1000L),
)

data class BuildingStats(
    val level: Long = 1,
    val wands: Long = 0,
    val wandsNeeded: Long
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

data class Gifts(
    val bright: Gift = Gift(type = "bright"),
    val special: Gift = Gift(type = "special"),
    val fairytale: Gift = Gift(type = "gifts"),
)

@Parcelize
data class Gift(
    val type: String = "bright",
    val level: Int = 1,
    val giftsOpened: Int = 0,
    val gifts: Int = 1,
) : Parcelable

@Parcelize
data class GiftInfo(
    val gift: Gift = Gift(),
    val minFirstReward: Int = 0,
    val maxFirstReward: Int = 0,
    val minSecondReward: Int = 0,
    val maxSecondReward: Int = 0,
    val minThirdReward: Int = 0,
    val maxThirdReward: Int = 0,
) : Parcelable

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

data class ItemShop(
    val name: String,
    val image: Int,
    val cost: Int
)

data class Inventory(
    val image: Int,
    var count: Int
)