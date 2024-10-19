package com.mobdeve_group45_mco

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve_group45_mco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.activity_main_rv_hours.adapter = HourlyAdapter(DataGenerator.hourlyWeatherData())
        viewBinding.activity_main_rv_hours.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.activity_main_rv_days.adapter = DailyAdapter(DataGenerator.dailyWeatherData())
        viewBinding.activity_main_rv_days.layoutManager = LinearLayoutManager(this)
    }
}