package com.example.appcsgo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcsgo.data.model.QuickAccessItem
import com.example.appcsgo.databinding.ItemQuickAccessBinding

class QuickAccessAdapter(
    private val onItemClick: (QuickAccessItem) -> Unit
) : RecyclerView.Adapter<QuickAccessAdapter.ViewHolder>() {

    private val items = mutableListOf<QuickAccessItem>()

    fun submitList(newItems: List<QuickAccessItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemQuickAccessBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuickAccessItem) {
            binding.quickAccessTitle.text = item.title ?: ""

            Glide.with(binding.root)
                .load(item.imageUrl)
                .into(binding.quickAccessImage)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuickAccessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
