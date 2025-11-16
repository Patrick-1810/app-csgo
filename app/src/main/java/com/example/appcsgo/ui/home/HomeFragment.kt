package com.example.appcsgo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcsgo.databinding.FragmentHomeBinding
import com.example.appcsgo.ui.crates.CratesAdapter
import com.example.appcsgo.ui.skins.SkinsAdapter
import com.example.appcsgo.ui.skins.SkinDetailActivity
import com.example.appcsgo.ui.highlights.HighlightDetailActivity
import com.example.appcsgo.ui.highlights.HighlightsAdapter
import com.example.appcsgo.ui.sticker.StickerDetailActivity
import com.example.appcsgo.ui.sticker.StickersAdapter
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var newCratesAdapter: CratesAdapter
    private lateinit var popularSkinsAdapter: SkinsAdapter
    private lateinit var stickersAdapter: StickersAdapter
    private lateinit var latestHighlightsAdapter: HighlightsAdapter
    //private lateinit var agentsAdapter: AgentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val factory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setupAdapters()
        setupObservers()

        viewModel.loadHome()

        return binding.root
    }

    private fun setupAdapters() {

        // ----------------- CRATES -----------------
        newCratesAdapter = CratesAdapter(emptyList())
        binding.newCratesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newCratesAdapter
        }

        // ---------------- SKINS -------------------
        popularSkinsAdapter = SkinsAdapter(emptyList()) { skin ->
            val json = Gson().toJson(skin)
            val intent = SkinDetailActivity.newIntent(requireContext(), skin)
            intent.putExtra("skin_json", json)
            startActivity(intent)
        }
        binding.PopularSkinsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularSkinsAdapter
        }

        // ---------------- HIGHLIGHTS --------------
        latestHighlightsAdapter = HighlightsAdapter(emptyList()) { highlight ->
            val json = Gson().toJson(highlight)
            val intent = HighlightDetailActivity.newIntent(requireContext(), json)
            startActivity(intent)
        }
        binding.latestHighlightsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = latestHighlightsAdapter
        }

        // ----------------- AGENTS ------------------
//        agentsAdapter = AgentsAdapter()
//        binding.agentsRecycler.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = agentsAdapter
//        }

        // ----------------- STICKERS -----------------
        stickersAdapter = StickersAdapter { sticker ->
            startActivity(StickerDetailActivity.intent(requireContext(), sticker))
        }
        binding.stickersRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = stickersAdapter
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                // ---------- ERRO ----------
                if (state.error != null) {
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }

                // ---------- CRATES ----------
                newCratesAdapter.updateList(state.newCrates)

                // ---------- SKINS ----------
                popularSkinsAdapter.submitList(state.popularSkins)

                // ---------- HIGHLIGHTS ----------
                latestHighlightsAdapter.submitList(state.highlights)

                // ---------- AGENTS ----------
                //agentsAdapter.submitList(state.agents)

                // ---------- STICKERS ----------
                stickersAdapter.submitList(state.stickers)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
