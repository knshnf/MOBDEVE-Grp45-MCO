package com.mobdeve_group45_mco

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
    }
}