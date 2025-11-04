package com.example.appcsgo.data.model

import java.io.Serializable

data class Highlight(
    val id: String,
    val name: String,
    val description: String?,
    val tournament_event: String?,
    val team0: String?,
    val team1: String?,
    val stage: String?,
    val map: String?,
    val image: String?,
    val video: String?
) : Serializable