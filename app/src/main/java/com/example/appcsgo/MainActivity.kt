package com.example.appcsgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appcsgo.databinding.ActivityMainBinding
import com.example.appcsgo.ui.agents.AgentsFragment
import com.example.appcsgo.ui.skins.SkinsFragment
import com.example.appcsgo.ui.highlights.HighlightsFragment
import com.example.appcsgo.ui.crates.CratesFragment
//import com.example.appcsgo.ui.agents.AgentsFragment
import com.example.appcsgo.ui.home.HomeFragment
import com.example.appcsgo.ui.sticker.StickersFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializa com Home
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_skins -> replaceFragment(SkinsFragment())
                R.id.nav_highlights -> replaceFragment(HighlightsFragment())
                R.id.nav_crates -> replaceFragment(CratesFragment())
                R.id.nav_agents -> replaceFragment(AgentsFragment())
                R.id.nav_stickers -> replaceFragment(StickersFragment())
                else -> false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
