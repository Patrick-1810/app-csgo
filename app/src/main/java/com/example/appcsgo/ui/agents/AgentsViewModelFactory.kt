package com.example.appcsgo.ui.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcsgo.data.repository.CsgoRepository

class AgentsViewModelFactory(private val repository: CsgoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}