package com.example.appcsgo.ui.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcsgo.data.model.Agent // Assumindo que você criou o data class Agent
import com.example.appcsgo.data.repository.CsgoRepository
import kotlinx.coroutines.launch

class AgentsViewModel(private val repository: CsgoRepository) : ViewModel() {

    // Lista completa de agentes
    private val _agents = MutableLiveData<List<Agent>>()
    // Lista de agentes filtrados (mostrada na UI)
    private val _filteredAgents = MutableLiveData<List<Agent>>()
    val filteredAgents: LiveData<List<Agent>> = _filteredAgents

    fun fetchAgents() {
        viewModelScope.launch {
            try {
                // A lista de agentes é obtida do CsgoRepository
                _agents.value = repository.getAgents()
                _filteredAgents.value = _agents.value
            } catch (e: Exception) {
                // Em caso de erro
                _filteredAgents.value = emptyList()
            }
        }
    }

    fun filterAgents(query: String) {
        _filteredAgents.value = _agents.value?.filter { agent ->
            // Filtra pelo nome do agente (ignorando case)
            agent.name.contains(query, ignoreCase = true)
        }
    }
}