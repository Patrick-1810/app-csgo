package com.example.appcsgo.ui.agents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appcsgo.data.repository.CsgoRepository
import com.example.appcsgo.databinding.FragmentAgentsBinding
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

        val repository = CsgoRepository()
        val factory = AgentsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AgentsViewModel::class.java]

        adapter = AgentsAdapter() { agent ->
            startActivity(AgentDetailActivity.newIntent(requireContext(), agent))
        }

        binding.recyclerViewAgents.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewAgents.adapter = adapter

        viewModel.filteredAgents.observe(viewLifecycleOwner) { filtered ->
            adapter.submitList(filtered)


            if (filtered.isNullOrEmpty()) {
                binding.tvErrorAgents.visibility = View.VISIBLE
            } else {
                binding.tvErrorAgents.visibility = View.GONE
            }
        }

        setupSearchView()

        viewModel.fetchAgents()
        return binding.root
    }


    private fun setupSearchView() {
        val searchEditText = binding.svSearchAgents

        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterAgents(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}