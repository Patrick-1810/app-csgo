package com.example.appcsgo.ui.crates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.repository.CratesRepository
import com.example.appcsgo.databinding.FragmentCratesBinding

class CratesFragment : Fragment() {

    private lateinit var binding: FragmentCratesBinding
    private lateinit var viewModel: CratesViewModel
    private lateinit var adapter: CratesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCratesBinding.inflate(inflater, container, false)

        val apiService = RetrofitClient.apiService
        val repository = CratesRepository(apiService)
        viewModel = CratesViewModel(repository)

        adapter = CratesAdapter(emptyList())
        binding.recyclerViewCrates.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCrates.adapter = adapter

        viewModel.crates.observe(viewLifecycleOwner) { crates ->
            adapter.updateList(crates)
        }

        viewModel.filteredCrates.observe(viewLifecycleOwner) { filtered ->
            adapter.updateList(filtered)
        }

        binding.svSearchCrates.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterCrates(newText ?: "")
                return true
            }

        })
        binding.btnBackSkins.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        viewModel.fetchCrates()
        return binding.root
    }
}
