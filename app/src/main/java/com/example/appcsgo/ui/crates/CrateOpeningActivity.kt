package com.example.appcsgo.ui.crates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appcsgo.R
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.repository.CratesRepository

class CrateOpeningActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CRATE_ID = "extra_crate_id"
        private const val EXTRA_CRATE_NAME = "extra_crate_name"

        fun newIntent(context: Context, crateId: String, crateName: String): Intent {
            return Intent(context, CrateOpeningActivity::class.java).apply {
                putExtra(EXTRA_CRATE_ID, crateId)
                putExtra(EXTRA_CRATE_NAME, crateName)
            }
        }
    }

    private lateinit var viewModel: CratesViewModel

    private lateinit var ivImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var resultContainer: View
    private lateinit var tvName: TextView
    private lateinit var tvWeapon: TextView
    private lateinit var tvRarity: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvCongrats: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crate_opening)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_crate_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        resultContainer = findViewById(R.id.resultContainer)
        tvName = findViewById(R.id.tv_name)
        tvWeapon = findViewById(R.id.tv_weapon)
        tvRarity = findViewById(R.id.tv_rarity)
        tvDescription = findViewById(R.id.tv_description)
        tvCongrats = findViewById(R.id.tv_congrats)

        val crateId = intent.getStringExtra(EXTRA_CRATE_ID) ?: return
        val crateName = intent.getStringExtra(EXTRA_CRATE_NAME) ?: "Crate"

        supportActionBar?.title = crateName

        ivImage.setImageResource(R.drawable.imganimation)
        tvCongrats.visibility = View.GONE

        val repository = CratesRepository(RetrofitClient.apiService)
        val factory = CratesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CratesViewModel::class.java]

        viewModel.openingResult.observe(this) { item ->
            if (item.isEmpty()) {
                progressBar.visibility = View.GONE
                resultContainer.visibility = View.VISIBLE
                tvCongrats.visibility = View.GONE
                tvName.text = "No items in crate"
                tvWeapon.text = ""
                tvRarity.text = ""
                tvDescription.text = ""
                ivImage.setImageResource(R.drawable.imganimation)
                return@observe
            }

            val rawName = item["name"] ?: item["weapon"] ?: item["skin"] ?: item["id"]
            val name = rawName?.toString()?.takeIf { it.isNotBlank() } ?: "Unknown item"

            val rawWeapon = item["weapon"] ?: item["type"]
            val weapon = rawWeapon?.toString()?.takeIf { it.isNotBlank() } ?: ""

            val rawRarity = item["rarity"]
            val rarity = rawRarity?.toString()?.takeIf { it.isNotBlank() } ?: "Unknown"

            val rawDescription = item["description"] ?: item["desc"] ?: ""
            val description = rawDescription.toString()

            val rawImage = item["image"] ?: item["icon"] ?: item["icon_url"]
            val imageUrl = rawImage?.toString()?.takeIf { it.isNotBlank() }

            ivImage.animate()
                .rotationBy(360f)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(700)
                .withStartAction {
                    progressBar.visibility = View.GONE
                    resultContainer.visibility = View.GONE
                    tvCongrats.visibility = View.GONE
                    ivImage.setImageResource(R.drawable.imganimation)
                }
                .withEndAction {
                    tvName.text = name
                    tvWeapon.text = if (weapon.isNotBlank()) "Tipo: $weapon" else ""
                    tvRarity.text = "Raridade: $rarity"
                    tvDescription.text = description

                    if (imageUrl != null) {
                        Glide.with(this)
                            .load(imageUrl)
                            .into(ivImage)
                    }

                    resultContainer.alpha = 0f
                    resultContainer.visibility = View.VISIBLE
                    resultContainer.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .start()

                    tvCongrats.text = "PARABÉNS!"
                    tvCongrats.visibility = View.VISIBLE
                    tvCongrats.scaleX = 0f
                    tvCongrats.scaleY = 0f
                    tvCongrats.alpha = 0f

                    tvCongrats.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .alpha(1f)
                        .setDuration(350)
                        .withEndAction {
                            tvCongrats.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(150)
                                .start()
                        }
                        .start()

                    ivImage.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200)
                        .start()
                }
                .start()
        }

        openCrate(crateId)
    }

    private fun openCrate(crateId: String) {
        progressBar.visibility = View.VISIBLE
        resultContainer.visibility = View.GONE
        tvCongrats.visibility = View.GONE
        viewModel.openCrate(crateId)
    }
}
