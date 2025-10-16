package com.example.appcsgo.ui.skins

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.appcsgo.databinding.ActivitySkinDetailBinding
import com.example.appcsgo.data.model.Skin

class SkinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySkinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val skin = intent.getSerializableExtra("skin") as? Skin

        if (skin != null) {
            binding.tvName.text = skin.name
            binding.tvDescription.text = skin.description ?: "Sem descrição"
            binding.tvWeapon.text = skin.weapon?.name ?: "Arma desconhecida"
            binding.tvRarity.text = skin.rarity?.name ?: "Raridade desconhecida"
            binding.ivImage.load(skin.image) {
                crossfade(true)
                placeholder(com.example.appcsgo.R.drawable.ic_placeholder)
                error(com.example.appcsgo.R.drawable.ic_error)
            }
        } else {
            binding.tvName.text = "Skin não encontrada"
        }
    }
}
