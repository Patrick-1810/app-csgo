package com.example.appcsgo.ui.skins

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcsgo.databinding.FragmentSkinsBinding
import com.example.appcsgo.ui.skins.SkinsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SkinsFragment : Fragment() {

    private var _binding: FragmentSkinsBinding? = null
    private val binding get() = _binding!!

    private val viewModel = SkinsViewModel()
    private lateinit var adapter: SkinsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkinsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o adapter
        adapter = SkinsAdapter(emptyList()) { skin ->
            val intent = Intent(requireContext(), SkinDetailActivity::class.java)
            intent.putExtra("skin", skin)
            startActivity(intent)
        }

        // Configura o RecyclerView
        binding.rvSkins.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSkins.adapter = adapter

        // Observa as skins filtradas
        lifecycleScope.launch {
            viewModel.filteredSkins.collectLatest {
                adapter.submitList(it)
            }
        }

        // Configura o campo de busca
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
