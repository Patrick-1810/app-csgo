package com.example.appcsgo.data.repository

import com.example.appcsgo.data.api.ApiService
import com.example.appcsgo.data.model.Crate

class CratesRepository(private val apiService: ApiService) {
    suspend fun getCrates(): List<Crate> = apiService.getCrates()
}
