package com.example.appcsgo.ui.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Agent
import com.example.appcsgo.data.repository.CsgoRepository
import kotlinx.coroutines.launch

class AgentsViewModel(private val repository: CsgoRepository) : ViewModel() {

    private val _agents = MutableLiveData<List<Agent>>()
    private val _filteredAgents = MutableLiveData<List<Agent>>()
    val filteredAgents: LiveData<List<Agent>> = _filteredAgents

    fun fetchAgents() {
        viewModelScope.launch {
            try {
                _agents.value = repository.getAgents()
                _filteredAgents.value = _agents.value
            } catch (e: Exception) {
                _filteredAgents.value = emptyList()
            }
        }
    }

    fun filterAgents(query: String) {
        _filteredAgents.value = _agents.value?.filter { agent ->
            agent.name.contains(query, ignoreCase = true)
        }
    }
}