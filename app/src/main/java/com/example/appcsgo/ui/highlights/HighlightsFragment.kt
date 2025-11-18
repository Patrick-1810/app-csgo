package com.example.appcsgo.ui.highlights

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// A importação de SearchView não é mais necessária se você só usar EditText
// import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.databinding.FragmentHighlightsBinding
import com.google.gson.Gson

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HighlightsFragment : Fragment() {

    private var _binding: FragmentHighlightsBinding? = null
    private val binding get() = _binding!!

    private val viewModel = HighlightsViewModel()

    private lateinit var adapter: HighlightsAdapter

    companion object {
        const val EXTRA_HIGHLIGHT_JSON = "highlight_json"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHighlightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HighlightsAdapter(emptyList()) { highlight ->
            navigateToDetail(highlight)
        }

        binding.recyclerViewHighlights.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        binding.recyclerViewHighlights.adapter = adapter

        observeViewModel()

        setupSearchView()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.filteredHighlights.collectLatest { highlights ->
                adapter.submitList(highlights)
                binding.tvErrorHighlights.visibility = if (highlights.isEmpty() && !viewModel.loading.value) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                binding.progressBarHighlights.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) binding.tvErrorHighlights.visibility = View.GONE
            }
        }
    }

    private fun setupSearchView() {

        val searchEditText = binding.svSearchHighlights

        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                viewModel.search(s.toString())
            }
        })

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(v.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun navigateToDetail(highlight: Highlight) {
        val json = Gson().toJson(highlight)
        val intent = Intent(requireContext(), HighlightDetailActivity::class.java)
        intent.putExtra(EXTRA_HIGHLIGHT_JSON, json)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}