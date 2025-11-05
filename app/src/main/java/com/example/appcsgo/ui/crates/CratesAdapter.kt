package com.example.appcsgo.ui.crates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.databinding.ItemCrateBinding

class CratesAdapter(private var crates: List<Crate>) :
    RecyclerView.Adapter<CratesAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCrateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCrateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crate = crates[position]
        holder.binding.tvCrateName.text = crate.name

        Glide.with(holder.itemView.context)
            .load(crate.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivCrateImage)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CrateDetailActivity::class.java)
            intent.putExtra("crate", crate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = crates.size

    fun updateList(newList: List<Crate>) {
        crates = newList
        notifyDataSetChanged()
    }
}
