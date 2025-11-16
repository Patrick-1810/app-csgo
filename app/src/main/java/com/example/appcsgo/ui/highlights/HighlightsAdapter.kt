package com.example.appcsgo.ui.highlights

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.databinding.ItemHighlightBinding
import com.google.gson.Gson

class HighlightsAdapter(
    private var highlights: List<Highlight>,
    private val onClick: (Highlight) -> Unit
) : RecyclerView.Adapter<HighlightsAdapter.HighlightViewHolder>() {

    fun submitList(newList: List<Highlight>) {
        highlights = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightViewHolder {
        val binding = ItemHighlightBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HighlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HighlightViewHolder, position: Int) {
        holder.bind(highlights[position])
    }

    override fun getItemCount() = highlights.size

    inner class HighlightViewHolder(
        private val binding: ItemHighlightBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Highlight) {

            binding.tvHighlightName.text = item.name

            // Carregando thumbnail
            binding.ivHighlightThumb.load(item.image)

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}
