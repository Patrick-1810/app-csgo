package com.example.appcsgo.data.repository

import com.example.appcsgo.data.api.ApiService
import com.example.appcsgo.data.model.Sticker

class StickersRepository(private val api: ApiService) {
    suspend fun fetchAll(): List<Sticker> = api.getStickers()
}
