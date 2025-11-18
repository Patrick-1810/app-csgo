package com.example.appcsgo.ui.sticker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.appcsgo.R

class StickersFragment : Fragment() {
    private lateinit var viewModel: StickersViewModel
    private lateinit var adapter: StickersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_stickers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.rv_stickers)
        val sv = view.findViewById<EditText>(R.id.sv_search)
        val tvError = view.findViewById<TextView>(R.id.tvErrorStickers)

        viewModel = ViewModelProvider(this, StickersViewModelFactory())
            .get(StickersViewModel::class.java)

        adapter = StickersAdapter { sticker ->
            startActivity(StickerDetailActivity.intent(requireContext(), sticker))
        }

        rv.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        rv.adapter = adapter
        rv.setHasFixedSize(true)

        sv.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filter(s.toString())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })



        viewModel.state.observe(viewLifecycleOwner) { s ->
            if (s.error != null) {
                Toast.makeText(requireContext(), s.error, Toast.LENGTH_SHORT).show()
                tvError.visibility = View.GONE
            } else if (s.filtered.isEmpty()) {
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE
            }

            adapter.submitList(s.filtered)
        }
    }
}