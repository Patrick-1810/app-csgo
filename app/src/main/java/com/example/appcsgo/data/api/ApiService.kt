package com.example.appcsgo.data.api

import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import retrofit2.http.GET
import  com.example.appcsgo.data.model.Crate

interface ApiService {
    @GET("skins.json")
    suspend fun getSkins(): List<Skin>
    @GET("stickers.json")
    suspend fun getStickers(): List<Sticker>

    @GET("highlights.json")
    suspend fun getHighlights(): List<Highlight>

    @GET("crates.json")
    suspend fun getCrates(): List<Crate>

}