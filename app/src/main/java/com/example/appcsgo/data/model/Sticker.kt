package com.example.appcsgo.data.model

data class Sticker(
    val id: String,
    val name: String,
    val description: String?,
    val rarity: Rarity?,
    val crates: List<SimpleCrate>?,
    val tournament_event: String?,
    val tournament_team: String?,
    val type: String?,
    val market_hash_name: String?,
    val effect: String?,
    val image: String?
)

data class SimpleCrate(val id: String?, val name: String?, val image: String?)