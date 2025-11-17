package com.example.appcsgo.ui.highlights

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import coil.load
import com.example.appcsgo.R
import com.example.appcsgo.data.model.Highlight
import com.example.appcsgo.databinding.ActivityHighlightDetailBinding
import com.google.gson.Gson


class HighlightDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighlightDetailBinding

    companion object {
        private const val EXTRA_JSON = "highlight_json"

        fun newIntent(context: Context, highlightJson: String): Intent {
            return Intent(context, HighlightDetailActivity::class.java).apply {
                putExtra(EXTRA_JSON, highlightJson)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHighlightDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -- SETA --
        val toolbar: Toolbar = binding.toolbarHighlightDetail
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }



        val json = intent.getStringExtra(EXTRA_JSON)
        val highlight = Gson().fromJson(json, Highlight::class.java)

        if (highlight != null) {
            binding.tvName.text = highlight.name
            binding.tvEventStage.text = "${highlight.tournament_event} - ${highlight.stage}"
            binding.tvTeamsMap.text = "${highlight.team0} vs ${highlight.team1} (${highlight.map})"
            binding.tvDescription.text = highlight.description

            if (!highlight.video.isNullOrEmpty()) {
                loadVideo(highlight.video!!)
            } else {
                showThumbnail(highlight.image)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadVideo(url: String) {
        binding.wvVideoPlayer.visibility = View.VISIBLE
        binding.ivImage.visibility = View.GONE

        val settings = binding.wvVideoPlayer.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        binding.wvVideoPlayer.webChromeClient = WebChromeClient()
        binding.wvVideoPlayer.webViewClient = WebViewClient()

        binding.wvVideoPlayer.loadUrl(url)
    }

    private fun showThumbnail(imageUrl: String?) {
        binding.ivImage.visibility = View.VISIBLE
        binding.wvVideoPlayer.visibility = View.GONE

        binding.ivImage.load(imageUrl ?: "") {
            placeholder(R.drawable.ic_placeholder)
            crossfade(true)
        }
    }
}
