package com.example.appcsgo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.data.repository.CsgoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class SkinsViewModel(
    private val repo: CsgoRepository = CsgoRepository()
) : ViewModel() {

    private val _allSkins = MutableStateFlow<List<Skin>>(emptyList())
    val allSkins: StateFlow<List<Skin>> = _allSkins

    private val _filteredSkins = MutableStateFlow<List<Skin>>(emptyList())
    val filteredSkins: StateFlow<List<Skin>> = _filteredSkins

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadSkins()
    }

    fun loadSkins() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val skins = repo.fetchSkins()
                _allSkins.value = skins
                _filteredSkins.value = skins
                Log.d("SkinsViewModel", "Skins carregadas com sucesso: ${skins.size}")
            } catch (e: Exception) {
                Log.e("SkinsViewModel", "Erro ao carregar skins: ${e.message}")
                _allSkins.value = emptyList()
                _filteredSkins.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun search(query: String) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            _filteredSkins.value = _allSkins.value
            return
        }
        _filteredSkins.value = _allSkins.value.filter { it.name.lowercase().contains(q) }
    }
}
