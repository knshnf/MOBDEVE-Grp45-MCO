package com.mobdeve_group45_mco.forecast

class Current(temperature: Number, isDay: Int, weatherCode: Int, time: String) {
    var temperature = temperature
        private set

    var isDay = isDay
        private set

    var weatherCode = weatherCode
        private set

    var time = time
        private set
}