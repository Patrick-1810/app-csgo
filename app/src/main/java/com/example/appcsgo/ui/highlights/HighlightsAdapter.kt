package com.example.appcsgo.ui.highlights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.data.model.QuickAccessItem
import com.example.appcsgo.data.model.QuickAccessType
import com.example.appcsgo.databinding.ItemHighlightBinding
import com.example.appcsgo.data.repository.QuickAccessRepository

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
            binding.ivHighlightThumb.load(item.image)

            val context = binding.root.context

            binding.root.setOnClickListener {
                val repo = QuickAccessRepository.getInstance(context)
                val quickItem = QuickAccessItem(
                    id = item.name,
                    title = item.name,
                    imageUrl = item.image,
                    type = QuickAccessType.HIGHLIGHT
                )
                repo.addQuickAccessItem(quickItem)
                onClick(item)
            }
        }
    }
}
