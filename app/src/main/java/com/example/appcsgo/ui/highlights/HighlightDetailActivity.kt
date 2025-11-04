package com.example.appcsgo.ui.highlights

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.databinding.ActivityHighlightDetailBinding
import com.google.gson.Gson

class HighlightDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighlightDetailBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighlightDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = intent.getStringExtra(HighlightsFragment.EXTRA_HIGHLIGHT_JSON)
        val highlight = Gson().fromJson(json, Highlight::class.java)

        if (highlight != null) {
            binding.tvName.text = highlight.name

            val eventStage = "${highlight.tournament_event ?: "Evento N/A"} - ${highlight.stage ?: "Estágio N/A"}"
            binding.tvEventStage.text = eventStage

            val teamsMap = "${highlight.team0 ?: "Time 0"} vs ${highlight.team1 ?: "Time 1"} (${highlight.map ?: "Mapa N/A"})"
            binding.tvTeamsMap.text = teamsMap

            binding.tvDescription.text = highlight.description ?: "Sem descrição."


            if (!highlight.video.isNullOrEmpty()) {
                loadVideo(highlight.video)
            } else {

                showThumbnail(highlight.image)
            }

        } else {
            binding.tvName.text = "Highlight não encontrado"
            binding.wvVideoPlayer.visibility = View.GONE
            binding.ivImage.visibility = View.GONE
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadVideo(videoUrl: String) {
        binding.wvVideoPlayer.visibility = View.VISIBLE
        binding.ivImage.visibility = View.GONE


        binding.wvVideoPlayer.settings.javaScriptEnabled = true
        binding.wvVideoPlayer.settings.domStorageEnabled = true
        binding.wvVideoPlayer.webChromeClient = WebChromeClient()
        binding.wvVideoPlayer.webViewClient = WebViewClient()

        binding.wvVideoPlayer.loadUrl(videoUrl)
    }

    private fun showThumbnail(imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            binding.ivImage.visibility = View.VISIBLE
            binding.wvVideoPlayer.visibility = View.GONE

            binding.ivImage.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.wvVideoPlayer.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.wvVideoPlayer.onResume()
    }

    override fun onDestroy() {
        binding.wvVideoPlayer.destroy()
        super.onDestroy()
    }
}