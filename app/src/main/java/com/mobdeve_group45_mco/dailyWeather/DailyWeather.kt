package com.mobdeve_group45_mco.dailyWeather

class DailyWeather(time: String, weatherCode: Int, minTemp: Number, maxtemp: Number) {
    var time = time
        private set

    var weatherCode = weatherCode
        private set

    var minTemp = minTemp
        private set

    var maxTemp = maxtemp
        private set
}