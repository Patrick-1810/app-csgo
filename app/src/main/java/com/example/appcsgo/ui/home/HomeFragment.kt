package com.example.appcsgo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.data.repository.QuickAccessRepository
import com.example.appcsgo.databinding.FragmentHomeBinding
import com.example.appcsgo.ui.agents.AgentDetailActivity
import com.example.appcsgo.ui.agents.AgentsAdapter
import com.example.appcsgo.ui.crates.CrateOpeningActivity
import com.example.appcsgo.ui.crates.CratesAdapter
import com.example.appcsgo.ui.highlights.HighlightDetailActivity
import com.example.appcsgo.ui.highlights.HighlightsAdapter
import com.example.appcsgo.ui.skins.SkinDetailActivity
import com.example.appcsgo.ui.skins.SkinsAdapter
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
    private lateinit var quickAccessAdapter: QuickAccessAdapter
    private lateinit var agentsAdapter: AgentsAdapter

    private var heroCrate: Crate? = null

    private val quickAccessRepository by lazy {
        QuickAccessRepository.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val factory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setupAdapters()
        setupObservers()
        setupOpenCrateButton()

        viewModel.loadHome()

        return binding.root
    }

    private fun setupAdapters() {
        quickAccessAdapter = QuickAccessAdapter { item -> }

        binding.quickAccessRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = quickAccessAdapter
        }

        newCratesAdapter = CratesAdapter(emptyList())
        binding.newCratesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newCratesAdapter
        }

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

        latestHighlightsAdapter = HighlightsAdapter(emptyList()) { highlight ->
            val json = Gson().toJson(highlight)
            val intent = HighlightDetailActivity.newIntent(requireContext(), json)
            startActivity(intent)
        }
        binding.latestHighlightsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = latestHighlightsAdapter
        }

        agentsAdapter = AgentsAdapter() { agent ->
            startActivity(AgentDetailActivity.newIntent(requireContext(), agent))
        }
        binding.featuredAgentsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = agentsAdapter
        }

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
                if (state.error != null) {
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }

                newCratesAdapter.updateList(state.newCrates)
                popularSkinsAdapter.submitList(state.popularSkins)
                latestHighlightsAdapter.submitList(state.highlights)
                agentsAdapter.submitList(state.agents)
                stickersAdapter.submitList(state.stickers)

                heroCrate = state.newCrates.firstOrNull()
            }
        }
    }

    private fun setupOpenCrateButton() {
        binding.openCrateButton.setOnClickListener {
            val crate = heroCrate ?: return@setOnClickListener
            val id = crate.id ?: return@setOnClickListener
            val name = crate.name ?: "Crate"
            val intent = CrateOpeningActivity.newIntent(requireContext(), id, name)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val items = quickAccessRepository.getQuickAccessItems()
        quickAccessAdapter.submitList(items)
        binding.quickAccessRecycler.isVisible = items.isNotEmpty()
        binding.quickAccessTitle.isVisible = items.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
