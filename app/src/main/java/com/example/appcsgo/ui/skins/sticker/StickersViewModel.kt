package com.example.appcsgo.ui.skins.sticker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Sticker
import com.example.appcsgo.data.repository.StickersRepository
import kotlinx.coroutines.launch
import java.util.Locale

data class StickersState(
    val loading: Boolean = true,
    val error: String? = null,
    val list: List<Sticker> = emptyList(),
    val filtered: List<Sticker> = emptyList(),
    val query: String = ""
)

class StickersViewModel(private val repo: StickersRepository) : ViewModel() {

    private val _state = MutableLiveData(StickersState())
    val state: LiveData<StickersState> = _state

    init { load() }

    fun load() {
        _state.value = _state.value?.copy(loading = true, error = null)
        viewModelScope.launch {
            try {
                val items = repo.fetchAll().sortedBy { it.name.lowercase(Locale.ROOT) }
                _state.value = StickersState(loading = false, list = items, filtered = items)
            } catch (t: Throwable) {
                _state.value = _state.value?.copy(
                    loading = false,
                    error = t.message ?: "Erro ao carregar stickers"
                )
            }
        }
    }

    fun filter(q: String) {
        val query = q.trim()
        val base = _state.value?.list ?: emptyList()
        val out = if (query.isEmpty()) base else base.filter { s ->
            s.name.contains(query, true) ||
                    (s.tournament_team?.contains(query, true) == true) ||
                    (s.tournament_event?.contains(query, true) == true)
        }
        _state.value = _state.value?.copy(query = query, filtered = out)
    }
}
