package com.example.appcsgo.data.repository

import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.api.RetrofitClient.apiService
import com.example.appcsgo.data.model.Highlight

class CsgoRepository {
    private val api = RetrofitClient.apiService

    suspend fun fetchSkins(): List<Skin> = api.getSkins()

    suspend fun getHighlights(): Result<List<Highlight>> = try {
        val highlights = apiService.getHighlights()
        Result.success(highlights)
    } catch (e: Exception) {
        Result.failure(e)
    }

}