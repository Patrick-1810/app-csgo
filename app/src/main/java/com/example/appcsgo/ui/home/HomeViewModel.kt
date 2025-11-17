
package com.example.appcsgo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Agent
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.model.Sticker
import com.example.appcsgo.data.repository.CratesRepository
import com.example.appcsgo.data.repository.CsgoRepository
import com.example.appcsgo.data.repository.StickersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val newCrates: List<Crate> = emptyList(),
    val popularSkins: List<Skin> = emptyList(),
    val stickers: List<Sticker> = emptyList(),
    val highlights: List<Highlight> = emptyList(),
    val agents: List<Agent> = emptyList()
)



class HomeViewModel(
    private val cratesRepository: CratesRepository,
    private val csgoRepository: CsgoRepository,
    private val stickersRepository: StickersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun loadHome() {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            try {
                val crates = cratesRepository.getCrates().take(5)
                val skins = csgoRepository.fetchSkins().take(5)
                val highlightsResult = csgoRepository.getHighlights()
                val highlights = highlightsResult.getOrNull()?.take(5) ?: emptyList()
                val agents = csgoRepository.getAgents().take(5)
                val stickers = stickersRepository.fetchAll().take(5)

                _state.value = _state.value.copy(
                    isLoading = false,
                    newCrates = crates,
                    popularSkins = skins,
                    highlights = highlights,
                    agents = agents,
                    stickers = stickers
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
