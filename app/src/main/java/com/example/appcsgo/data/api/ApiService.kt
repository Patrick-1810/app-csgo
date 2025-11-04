package com.example.appcsgo.data.api

import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import retrofit2.http.GET

interface ApiService {
    @GET("skins.json")
    suspend fun getSkins(): List<Skin>
    @GET("stickers.json")
    suspend fun getStickers(): List<Sticker>

    @GET("highlights.json")
    suspend fun getHighlights(): List<Highlight>
}