package com.mobdeve_group45_mco

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.mobdeve_group45_mco.databinding.ActivitySearchBinding

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var bottomNavigationView = findViewById<NavigationBarView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> {
                    val navIntent = Intent(this, MainActivity::class.java)
                    navIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(navIntent)
                    true
                }
                R.id.miSearch -> {
                    val navIntent = Intent(this, Search::class.java)
                    navIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(navIntent)
                    true
                }
                R.id.miProfile -> {
                    val navIntent = Intent(this, profile_page::class.java)
                    navIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(navIntent)
                    true
                }

                else -> false
            }
        }
    }
}