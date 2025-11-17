package com.example.appcsgo.ui.agents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appcsgo.data.repository.CsgoRepository
import com.example.appcsgo.databinding.FragmentAgentsBinding // Necessário criar o layout XML
import com.example.appcsgo.ui.agents.AgentsViewModelFactory

class AgentsFragment : Fragment() {

    private var _binding: FragmentAgentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AgentsViewModel
    private lateinit var adapter: AgentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentsBinding.inflate(inflater, container, false)

        // 1. Inicializa o ViewModel e Factory
        // Usamos CsgoRepository, que não precisa de ApiService no construtor (como no HomeViewModelFactory)
        val repository = CsgoRepository()
        val factory = AgentsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AgentsViewModel::class.java]

        // 2. Inicializa o Adapter (reusa o AgentsAdapter)
        adapter = AgentsAdapter() { agent ->
            startActivity(AgentDetailActivity.newIntent(requireContext(), agent))
        }

        // 3. Configura o RecyclerView
        binding.recyclerViewAgents.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewAgents.adapter = adapter

        // 4. Observa a lista filtrada
        viewModel.filteredAgents.observe(viewLifecycleOwner) { filtered ->
            adapter.submitList(filtered)
        }

        // 5. Configura a busca/filtro (similar ao CratesFragment)
        binding.svSearchAgents.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterAgents(newText ?: "")
                return true
            }
        })

        // 6. Carrega os dados
        viewModel.fetchAgents()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}