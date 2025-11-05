package com.example.appcsgo.data.model

import java.io.Serializable

data class Crate(
    val id: String?,
    val name: String?,
    val image: String?,
    val released_at: String?,
    val contains: List<Map<String, Any>>?
) : Serializable
