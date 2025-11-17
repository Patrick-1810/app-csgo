package com.example.appcsgo.ui.sticker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Sticker
import com.google.gson.Gson


class StickerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar_sticker_detail) // Ou binding.toolbarStickerDetail
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val json = intent.getStringExtra(EXTRA_JSON) ?: ""
        val sticker = Gson().fromJson(json, Sticker::class.java)

        val iv = findViewById<ImageView>(R.id.iv_image)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvRarity = findViewById<TextView>(R.id.tv_rarity)
        val tvDesc = findViewById<TextView>(R.id.tv_description)

        Glide.with(iv).load(sticker.image).into(iv)
        tvName.text = sticker.name
        tvRarity.text = "Raridade: ${sticker.rarity?.name ?: "-"}"
        tvDesc.text = sticker.description ?: ""
        title = "Sticker"
    }

    companion object {
        private const val EXTRA_JSON = "extra_sticker_json"

        fun intent(ctx: Context, sticker: Sticker): Intent {
            val json = Gson().toJson(sticker)
            return Intent(ctx, StickerDetailActivity::class.java).putExtra(EXTRA_JSON, json)
        }
    }
}