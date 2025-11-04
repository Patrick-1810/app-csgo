package com.example.appcsgo.ui.highlights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.databinding.ItemHighlightBinding

class HighlightsAdapter(
    private var items: List<Highlight> = listOf(),
    private val onClick: (Highlight) -> Unit
) : RecyclerView.Adapter<HighlightsAdapter.VH>() {

    fun submitList(newList: List<Highlight>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemHighlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VH(private val binding: ItemHighlightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(highlight: Highlight) {
            binding.tvHighlightName.text = highlight.name

            val infoText = "${highlight.tournament_event ?: "N/A"} | ${highlight.map ?: "N/A"}"
            binding.tvHighlightInfo.text = infoText

            binding.ivHighlightImage.load(highlight.image) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }

            binding.root.setOnClickListener { onClick(highlight) }
        }
    }
}