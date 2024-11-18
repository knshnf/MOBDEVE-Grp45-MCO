package com.mobdeve_group45_mco.forecast

import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather

class Forecast(hourly: ArrayList<HourlyWeather>, daily: ArrayList<DailyWeather>, current: Current,
               latitude: Number, longitude: Number) {
    var hourly = hourly
        private set

    var daily = daily
        private set

    var current = current
        private set

    var latitude =  latitude
        private set

    var longitude =  longitude
        private set
}