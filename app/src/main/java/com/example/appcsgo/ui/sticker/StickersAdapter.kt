package com.example.appcsgo.ui.sticker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Sticker

class StickersAdapter(
    private val onClick: (Sticker) -> Unit
) : ListAdapter<Sticker, StickersAdapter.VH>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Sticker>() {
            override fun areItemsTheSame(old: Sticker, new: Sticker) = old.id == new.id
            override fun areContentsTheSame(old: Sticker, new: Sticker) = old == new
        }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView as CardView
        val iv: ImageView = itemView.findViewById(R.id.iv_sticker_image)
        val name: TextView = itemView.findViewById(R.id.tv_sticker_name)
        val subtitle: TextView = itemView.findViewById(R.id.tv_sticker_subtitle)
        val rarity: TextView = itemView.findViewById(R.id.tv_sticker_rarity)
        val desc: TextView = itemView.findViewById(R.id.tv_sticker_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sticker, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val s = getItem(position)

        h.name.text = s.name

        val sub = listOfNotNull(s.tournament_team, s.tournament_event).joinToString(" • ")
        h.subtitle.text = sub
        h.subtitle.isVisible = sub.isNotBlank()

        val rarityName = s.rarity?.name
        val rarityColor = s.rarity?.color
        if (!rarityName.isNullOrBlank()) {
            h.rarity.text = rarityName
            h.rarity.visibility = View.VISIBLE
            if (!rarityColor.isNullOrBlank()) {
                runCatching { Color.parseColor(rarityColor) }.onSuccess { c ->
                    h.rarity.setBackgroundColor(c)
                }
            } else {
                h.rarity.setBackgroundColor(0xFF666666.toInt())
            }
        } else {
            h.rarity.visibility = View.GONE
        }

        if (!s.description.isNullOrBlank()) {
            h.desc.text = s.description
            h.desc.visibility = View.VISIBLE
        } else {
            h.desc.visibility = View.GONE
        }

        Glide.with(h.iv).load(s.image).into(h.iv)
        h.card.setOnClickListener { onClick(s) }
    }
}