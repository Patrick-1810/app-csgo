package com.example.appcsgo.data.model

enum class QuickAccessType {
    SKIN,
    STICKER,
    HIGHLIGHT,
    CRATE,
    AGENT
}

data class QuickAccessItem(
    val id: String?,
    val title: String?,
    val imageUrl: String?,
    val type: QuickAccessType
)
