package com.example.appcsgo.ui.skins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.databinding.ItemSkinBinding

class SkinsAdapter(
    private var items: List<Skin> = listOf(),
    private val onClick: (Skin) -> Unit
) : RecyclerView.Adapter<SkinsAdapter.VH>() {

    fun submitList(newList: List<Skin>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSkinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VH(private val binding: ItemSkinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(skin: Skin) {
            binding.tvSkinName.text = skin.name
            binding.tvSkinWeapon.text = skin.weapon?.name ?: ""
            binding.ivSkinImage.load(skin.image) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            binding.root.setOnClickListener { onClick(skin) }
        }
    }
}
