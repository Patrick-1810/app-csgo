package com.example.appcsgo.ui.crates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcsgo.data.repository.CratesRepository

class CratesViewModelFactory(
    private val repository: CratesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CratesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CratesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
