package com.example.appcsgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcsgo.ui.skins.SkinsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SkinsFragment())
                .commit()
        }
    }
}
