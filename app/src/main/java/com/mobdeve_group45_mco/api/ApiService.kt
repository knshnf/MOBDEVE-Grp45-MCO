package com.mobdeve_group45_mco.api

import com.google.gson.JsonObject
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface ApiService {
    @GET("/v1/forecast")
    fun getForecast(
        @Query("latitude") latitude: Number,
        @Query("longitude") longitude: Number,
        @Query("current") current: String = "temperature_2m,is_day,weather_code",
        @Query("hourly") hourly: String = "temperature_2m,apparent_temperature,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 14
    ): Call<JsonObject>

    // https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=10&language=en&format=json
    @GET("/v1/search")
    fun getLocations(
        @Query("name") name: String,
        @Query("count") count: Number = 5,
    ): Call<JsonObject>

    @GET("/v1/reverse")
    fun reverseGeocode(
        @Query("key") apiKey: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("format") format: String = "json"
    ): Call<JsonObject>
}