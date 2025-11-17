package com.example.appcsgo.data.repository

import com.example.appcsgo.data.api.DummyApiService
import com.example.appcsgo.data.model.Crate

class FakeCratesRepository(
    private val fakeList: List<Crate>
) : CratesRepository(apiService = DummyApiService()) {

    override suspend fun getCrates(): List<Crate> = fakeList
}
