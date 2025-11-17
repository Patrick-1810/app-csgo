package com.example.appcsgo.ui.skins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appcsgo.R
import com.example.appcsgo.data.model.QuickAccessItem
import com.example.appcsgo.data.model.QuickAccessType
import com.example.appcsgo.data.model.Skin
import com.example.appcsgo.databinding.ItemSkinBinding
import com.example.appcsgo.data.repository.QuickAccessRepository
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

            val context = binding.root.context

            binding.root.setOnClickListener {
                val repo = QuickAccessRepository.getInstance(context)
                val item = QuickAccessItem(
                    id = skin.name,
                    title = skin.name,
                    imageUrl = skin.image,
                    type = QuickAccessType.SKIN
                )
                repo.addQuickAccessItem(item)
                onClick(skin)
            }
        }
    }
}
