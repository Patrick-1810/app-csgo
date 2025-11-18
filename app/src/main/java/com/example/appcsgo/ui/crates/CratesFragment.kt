package com.example.appcsgo.ui.crates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.repository.CratesRepository
import com.example.appcsgo.databinding.FragmentCratesBinding
import android.widget.EditText
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
        binding.recyclerViewCrates.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCrates.adapter = adapter

        viewModel.filteredCrates.observe(viewLifecycleOwner) { filtered ->
            adapter.updateList(filtered)

            if (filtered.isNullOrEmpty()) {
                binding.tvErrorCrates.visibility = View.VISIBLE
            } else {
                binding.tvErrorCrates.visibility = View.GONE
            }
        }

        setupSearchView()

        viewModel.fetchCrates()
        return binding.root
    }

    private fun setupSearchView() {

        val searchEditText = binding.svSearchCrates

        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterCrates(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

    }
}