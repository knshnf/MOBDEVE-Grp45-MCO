package com.mobdeve_group45_mco

import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather

class DataGenerator {
    companion object {
        private val hour1 : HourlyWeather = HourlyWeather("NOW", R.drawable.sun, "27°")
        private val hour2 : HourlyWeather = HourlyWeather("10AM", R.drawable.sun, "27°")
        private val hour3 : HourlyWeather = HourlyWeather("11AM", R.drawable.sun, "27°")
        private val hour4 : HourlyWeather = HourlyWeather("12PM", R.drawable.sun, "28°")
        private val hour5 : HourlyWeather = HourlyWeather("1PM", R.drawable.sun, "29°")
        private val hour6 : HourlyWeather = HourlyWeather("2PM", R.drawable.sun, "30°")
        private val hour7 : HourlyWeather = HourlyWeather("3PM", R.drawable.sun, "30°")
        private val hour8 : HourlyWeather = HourlyWeather("4PM", R.drawable.cloudy, "31°")
        private val hour9 : HourlyWeather = HourlyWeather("5PM", R.drawable.cloudy, "31°")
        private val hour10 : HourlyWeather = HourlyWeather("6PM", R.drawable.cloudy, "30°")
        private val day1 : DailyWeather = DailyWeather("Today", R.drawable.sun, "27° - 31°")
        private val day2 : DailyWeather = DailyWeather("Sun", R.drawable.storm, "27° - 31°")
        private val day3 : DailyWeather = DailyWeather("Mon", R.drawable.cloudy, "27° - 32°")
        private val day4 : DailyWeather = DailyWeather("Tue", R.drawable.raining, "26° - 31°")
        private val day5 : DailyWeather = DailyWeather("Wed", R.drawable.raining, "26° - 29°")
        private val day6 : DailyWeather = DailyWeather("Thu", R.drawable.storm, "26° - 29°")
        private val day7 : DailyWeather = DailyWeather("Fri", R.drawable.raining, "27° - 29°")
        private val day8 : DailyWeather = DailyWeather("Sat", R.drawable.cloudy, "27° - 30°")

        fun hourlyWeatherData(): ArrayList<HourlyWeather> {
            return arrayListOf<HourlyWeather>(hour1, hour2, hour3, hour4, hour5, hour6, hour7, hour8, hour9, hour10)
        }

        fun dailyWeatherData(): ArrayList<DailyWeather> {
            return arrayListOf<DailyWeather>(day1, day2, day3, day4, day5, day6, day7, day8)
        }
    }
}
