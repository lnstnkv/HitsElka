package com.tsu.hitselka.model

data class PlayerInfo(
    val stats: Stats = Stats(),
    val resources: Resources = Resources(),
    val firstYearStats: FirstYearStats = FirstYearStats(),
    val firstYearInventory: List<Toy> = listOf(),
    val settings: Settings = Settings()
)

data class Stats(
    val currentLevel: Int = 1,
    val currentLevelWandsUsed: Int = 0,
    val wandsUsed: Int = 0,
)

data class Resources(
    val wands: Int = 500,
    val rubies: Int = 50,
    val moneys: Int = 50,
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