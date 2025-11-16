package com.example.appcsgo.ui.crates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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
            // Assumindo que o objeto Crate é Parcelable ou Serializable para ser passado no Intent
            intent.putExtra("crate", crate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = crates.size

    // --- CÓDIGO MODIFICADO ---
    fun updateList(newList: List<Crate>) {
        val diffCallback = CrateDiffCallback(this.crates, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.crates = newList // Atualiza a lista interna
        diffResult.dispatchUpdatesTo(this) // Usa as mudanças específicas calculadas
    }

    private class CrateDiffCallback(
        private val oldList: List<Crate>,
        private val newList: List<Crate>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Verifica se o mesmo item (baseado em um ID único, como o nome) está nas duas listas
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Verifica se o conteúdo do item é o mesmo (usando a igualdade padrão do data class)
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}