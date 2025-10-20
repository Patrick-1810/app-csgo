package com.example.appcsgo.ui.skins.sticker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcsgo.R
import com.example.appcsgo.ui.skins.SkinsFragment

class StickersFragment : Fragment() {

    private lateinit var viewModel: StickersViewModel
    private lateinit var adapter: StickersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_stickers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_stickers)
        val sv = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_search)
        val btnBack = view.findViewById<Button>(R.id.btn_back_skins)

        viewModel = ViewModelProvider(this, StickersViewModelFactory())
            .get(StickersViewModel::class.java)

        adapter = StickersAdapter { sticker ->
            startActivity(StickerDetailActivity.intent(requireContext(), sticker))
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        rv.setHasFixedSize(true)

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filter(query.orEmpty())
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter(newText.orEmpty())
                return true
            }
        })

        viewModel.state.observe(viewLifecycleOwner) { s ->
            if (s.error != null) {
                Toast.makeText(requireContext(), s.error, Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(s.filtered)
        }

        btnBack?.setOnClickListener {
            val popped = parentFragmentManager.popBackStackImmediate()
            if (!popped) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SkinsFragment())
                    .commit()
            }
        }
    }
}
