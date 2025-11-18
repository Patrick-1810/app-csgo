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
import com.example.appcsgo.R
import com.example.appcsgo.databinding.FragmentSkinsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.appcsgo.ui.sticker.StickersFragment
import com.google.gson.Gson
import com.example.appcsgo.ui.crates.CratesFragment

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

        adapter = SkinsAdapter(emptyList()) { skin ->
            val json = Gson().toJson(skin)
            val intent = Intent(requireContext(), SkinDetailActivity::class.java)
            intent.putExtra("skin_json", json)
            startActivity(intent)
        }

        binding.rvSkins.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        binding.rvSkins.adapter = adapter

        lifecycleScope.launch {
            viewModel.filteredSkins.collectLatest {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.filteredSkins.collectLatest { skins ->
                adapter.submitList(skins)
                if (skins.isEmpty()) {
                    binding.tvErrorSkins.visibility = View.VISIBLE
                } else {
                    binding.tvErrorSkins.visibility = View.GONE
                }
            }
        }

        binding.svSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                viewModel.search(s.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
