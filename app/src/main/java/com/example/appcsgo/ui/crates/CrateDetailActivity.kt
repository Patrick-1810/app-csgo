package com.example.appcsgo.ui.crates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Crate

class CrateDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crate_detail)

        val ivImage = findViewById<android.widget.ImageView>(R.id.iv_image)
        val tvName = findViewById<android.widget.TextView>(R.id.tv_name)
        val tvReleased = findViewById<android.widget.TextView>(R.id.tv_released)
        val tvItems = findViewById<android.widget.TextView>(R.id.tv_items)

        val crate = intent.getSerializableExtra("crate") as? Crate

        crate?.let {
            tvName.text = it.name
            tvReleased.text = "Lançamento: ${it.released_at ?: "Desconhecido"}"

            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivImage)

            val itemsText = it.contains?.joinToString("\n") { item ->
                val name = item["name"] ?: "Item sem nome"
                val rarity = (item["rarity"] as? Map<*, *>)?.get("name") ?: "Raridade desconhecida"
                "- $name ($rarity)"
            } ?: "Nenhum item listado."

            tvItems.text = "Itens contidos:\n$itemsText"
        }
    }
}
