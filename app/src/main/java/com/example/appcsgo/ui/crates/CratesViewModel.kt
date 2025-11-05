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
}
