package com.example.appcsgo.ui.agents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Agent
import com.example.appcsgo.data.model.QuickAccessItem
import com.example.appcsgo.data.model.QuickAccessType
import com.example.appcsgo.data.repository.QuickAccessRepository
import com.example.appcsgo.databinding.ItemAgentBinding

class AgentsAdapter(private val onClick: (Agent) -> Unit) :
    ListAdapter<Agent, AgentsAdapter.AgentViewHolder>(AgentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        val agent = getItem(position)
        holder.bind(agent, onClick)
    }

    class AgentViewHolder(private val binding: ItemAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(agent: Agent, onClick: (Agent) -> Unit) {
            binding.tvAgentName.text = agent.name

            Glide.with(binding.ivAgentImage.context)
                .load(agent.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivAgentImage)

            val context = binding.root.context

            itemView.setOnClickListener {
                val repo = QuickAccessRepository.getInstance(context)
                val item = QuickAccessItem(
                    id = agent.id.toString(),
                    title = agent.name,
                    imageUrl = agent.image,
                    type = QuickAccessType.AGENT
                )
                repo.addQuickAccessItem(item)
                onClick(agent)
            }
        }
    }

    private class AgentDiffCallback : DiffUtil.ItemCallback<Agent>() {
        override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem == newItem
        }
    }
}
