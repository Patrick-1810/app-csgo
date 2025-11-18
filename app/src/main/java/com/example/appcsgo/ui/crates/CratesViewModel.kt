package com.example.appcsgo.ui.crates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.data.repository.CratesRepository
import kotlinx.coroutines.launch

class CratesViewModel(private val repository: CratesRepository) : ViewModel() {

    private val _crates = MutableLiveData<List<Crate>>()
    val crates: LiveData<List<Crate>> = _crates

    private val _filteredCrates = MutableLiveData<List<Crate>>()
    val filteredCrates: LiveData<List<Crate>> = _filteredCrates

    private val _openingResult = MutableLiveData<Map<String, Any>>()
    val openingResult: LiveData<Map<String, Any>> = _openingResult

    fun fetchCrates() {
        viewModelScope.launch {
            _crates.value = repository.getCrates()
            _filteredCrates.value = _crates.value
        }
    }

    fun filterCrates(query: String) {
        _filteredCrates.value = _crates.value?.filter { crate ->
            crate.name?.contains(query, ignoreCase = true) == true
        }
    }

    fun openCrate(crateId: String) {
        viewModelScope.launch {
            try {
                val allCrates = repository.getCrates()
                val selectedCrate = allCrates.firstOrNull { it.id == crateId }
                val crateWithItems = when {
                    selectedCrate?.contains?.isNotEmpty() == true -> selectedCrate
                    else -> allCrates.firstOrNull { it.contains?.isNotEmpty() == true }
                }

                val items = crateWithItems?.contains ?: emptyList()

                val result: Map<String, Any> = if (items.isNotEmpty()) {
                    items.random()
                } else if (selectedCrate != null) {
                    val map = mutableMapOf<String, Any>()
                    selectedCrate.name?.let { map["name"] = it }
                    selectedCrate.image?.let { map["image"] = it }
                    map["rarity"] = "N/A"
                    map
                } else {
                    emptyMap()
                }

                _openingResult.value = result
            } catch (e: Exception) {
                _openingResult.value = emptyMap()
            }
        }
    }
}
