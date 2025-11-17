package com.example.appcsgo.data.model

import java.io.Serializable

data class Agent(
    val id: String,
    val name: String,
    val description: String?,
    val rarity: AgentRarity?,
    val collections: List<AgentCollection>?,
    val team: AgentTeam?,
    val market_hash_name: String,
    val image: String
) : Serializable // Torne-o Serializable para passar no Intent

data class AgentRarity(
    val id: String,
    val name: String,
    val color: String
) : Serializable

data class AgentCollection(
    val id: String,
    val name: String,
    val image: String
) : Serializable

data class AgentTeam(
    val id: String,
    val name: String
) : Serializable