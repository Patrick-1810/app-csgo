package com.example.appcsgo.ui.agents

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Agent
import com.example.appcsgo.databinding.ActivityAgentDetailBinding // Assumindo este nome

class AgentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgentDetailBinding

    companion object {
        private const val EXTRA_AGENT = "agent"
        fun newIntent(context: Context, agent: Agent): Intent {
            return Intent(context, AgentDetailActivity::class.java).apply {
                putExtra(EXTRA_AGENT, agent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_agent_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val agent = intent.getSerializableExtra(EXTRA_AGENT) as? Agent

        agent?.let {
            binding.tvName.text = it.name
            binding.tvTeam.text = "Time: ${it.team?.name ?: "Desconhecido"}"
            binding.tvDescription.text = it.description

            val rarityName = it.rarity?.name ?: "Desconhecida"
            binding.tvRarity.text = "Raridade: $rarityName"

            it.rarity?.color?.let { colorHex ->
                try {
                    binding.tvRarity.setTextColor(Color.parseColor(colorHex))
                } catch (e: IllegalArgumentException) {
                    // Cor padrão se o Hex for inválido
                    binding.tvRarity.setTextColor(Color.WHITE)
                }
            }


            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivImage)
        }
    }
}