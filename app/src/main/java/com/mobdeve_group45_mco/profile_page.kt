package com.mobdeve_group45_mco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.navigation.NavigationBarView

class profile_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var logoutBtn = findViewById<Button>(R.id.activity_profile_btn_Logout)
        logoutBtn.setOnClickListener {
            val navIntent = Intent(this, login_page::class.java)
            navIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(navIntent)
        }

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