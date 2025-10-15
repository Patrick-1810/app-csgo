package com.example.appcsgo.data.repository

import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import com.example.appcsgo.data.remote.RetrofitClient

class CsgoRepository {
    private val api = RetrofitClient.apiService

    suspend fun fetchSkins(): List<Skin> = api.getSkins()

    suspend fun fetchStickers(): List<Sticker> = api.getStickers()

}