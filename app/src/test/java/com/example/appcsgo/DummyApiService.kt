package com.example.appcsgo.data.api

import com.example.appcsgo.data.model.Agent
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker

class DummyApiService : ApiService {

    override suspend fun getSkins(): List<Skin> = emptyList()

    override suspend fun getStickers(): List<Sticker> = emptyList()

    override suspend fun getHighlights(): List<Highlight> = emptyList()

    override suspend fun getCrates(): List<Crate> = emptyList()
    override suspend fun getAgents(): List<Agent> {
        TODO("Not yet implemented")
    }
}
