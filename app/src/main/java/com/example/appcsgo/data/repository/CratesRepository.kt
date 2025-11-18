package com.example.appcsgo.data.repository

import com.example.appcsgo.data.api.ApiService
import com.example.appcsgo.data.model.Crate

open class CratesRepository(open val apiService: ApiService) {

    open suspend fun getCrates(): List<Crate> = apiService.getCrates()

    open suspend fun getCrateById(crateId: String): Crate? {
        return getCrates().firstOrNull { it.id == crateId }
    }
}
