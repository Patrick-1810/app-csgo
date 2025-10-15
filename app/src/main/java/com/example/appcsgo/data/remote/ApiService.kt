package com.example.appcsgo.data.remote

import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import retrofit2.http.GET

interface ApiService {
    @GET("skins.json")
    suspend fun getSkins(): List<Skin>

    @GET("stickers.json")
    suspend fun getStickers(): List<Sticker>
}