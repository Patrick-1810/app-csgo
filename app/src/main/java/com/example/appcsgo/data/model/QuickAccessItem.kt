package com.example.appcsgo.data.model

enum class QuickAccessType {
    SKIN,
    STICKER,
    HIGHLIGHT,
    CRATE
}

data class QuickAccessItem(
    val id: String?,
    val title: String?,
    val imageUrl: String?,
    val type: QuickAccessType
)
