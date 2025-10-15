package com.example.appcsgo.data.model

data class Skin(
    val id: String,
    val name: String,
    val description: String?,
    val weapon: Weapon?,
    val category: Category?,
    val pattern: Pattern?,
    val min_float: Double?,
    val max_float: Double?,
    val rarity: Rarity?,
    val stattrak: Boolean?,
    val souvenir: Boolean?,
    val paint_index: String?,
    val image: String?
)

data class Weapon(val id: String?, val weapon_id: Int?, val name: String?)
data class Category(val id: String?, val name: String?)
data class Pattern(val id: String?, val name: String?)
data class Rarity(val id: String?, val name: String?, val color: String?)