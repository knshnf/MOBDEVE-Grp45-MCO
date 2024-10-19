package com.mobdeve_group45_mco

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.mobdeve_group45_mco.databinding.ActivityMainBinding
import com.mobdeve_group45_mco.hourlyWeather.HourlyAdapter
import com.mobdeve_group45_mco.dailyWeather.DailyAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.activityMainRvHours.adapter = HourlyAdapter(DataGenerator.hourlyWeatherData())
        viewBinding.activityMainRvHours.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.activityMainRvDays.adapter = DailyAdapter(DataGenerator.dailyWeatherData())
        viewBinding.activityMainRvDays.layoutManager = LinearLayoutManager(this)

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