package com.example.appcsgo.ui.highlights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.repository.CsgoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HighlightsViewModel(
    private val repo: CsgoRepository = CsgoRepository()
) : ViewModel() {

    private val _allHighlights = MutableStateFlow<List<Highlight>>(emptyList())
    private val _filteredHighlights = MutableStateFlow<List<Highlight>>(emptyList())
    val filteredHighlights: StateFlow<List<Highlight>> = _filteredHighlights

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    init {
        loadHighlights()
    }

    fun loadHighlights() {
        viewModelScope.launch {
            try {
                _loading.value = true

                val result = repo.getHighlights()

                result.onSuccess { highlights ->
                    _allHighlights.value = highlights
                    _filteredHighlights.value = highlights
                    Log.d("HighlightsViewModel", "Highlights carregados com sucesso: ${highlights.size}")
                }.onFailure { e ->
                    Log.e("HighlightsViewModel", "Erro ao carregar highlights: ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("HighlightsViewModel", "Exceção inesperada: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun search(query: String) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            _filteredHighlights.value = _allHighlights.value
            return
        }

        _filteredHighlights.value = _allHighlights.value.filter { highlight ->
            highlight.name.lowercase().contains(q) ||
                    highlight.map?.lowercase()?.contains(q) == true ||
                    highlight.tournament_event?.lowercase()?.contains(q) == true
        }
    }
}