package com.mobdeve_group45_mco.utils

import android.util.Log
import com.mobdeve_group45_mco.R
import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.forecast.Current
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.forecast.Location
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        // Function to get the day of the week for a given date string
        fun getDayOfWeek(dateString: String): String {
            try {
                // Define the input date format (assuming input is "yyyy-MM-dd")
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date = formatter.parse(dateString)

                // Use Calendar to get the day of the week
                val calendar = Calendar.getInstance()
                calendar.time = date
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

                // Convert the day of the week to a string
                return when (dayOfWeek) {
                    Calendar.SUNDAY -> "Sunday"
                    Calendar.MONDAY -> "Monday"
                    Calendar.TUESDAY -> "Tuesday"
                    Calendar.WEDNESDAY -> "Wednesday"
                    Calendar.THURSDAY -> "Thursday"
                    Calendar.FRIDAY -> "Friday"
                    Calendar.SATURDAY -> "Saturday"
                    else -> "Unknown"
                }
            } catch (e: Exception) {
                // Handle invalid date format
                return "Invalid date format"
            }
        }

        // Function to get the hour in 12-hour format with AM/PM
        fun getHourIn12HourFormat(dateString: String): String {
            try {
                // Define the input date format (assuming input is "yyyy-MM-dd'T'HH:mm")
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
                val date = formatter.parse(dateString)

                // Use SimpleDateFormat to format the hour in 12-hour format with AM/PM
                val hourFormatter = SimpleDateFormat("h a")  // 'h' for 12-hour format, 'a' for AM/PM
                return hourFormatter.format(date)
            } catch (e: Exception) {
                // Handle invalid date format
                return "Invalid date format"
            }
        }

        fun getWeatherDescription(weatherCode: Int): String {
            return when (weatherCode) {
                0 -> "Clear sky"
                1 -> "Mainly clear"
                2 -> "Partly cloudy"
                3 -> "Cloudy"
                4 -> "Overcast"
                5 -> "Mist"
                6 -> "Fog"
                7 -> "Haze"
                8 -> "Dust"
                9 -> "Smoke"
                10 -> "Volcanic ash"
                11 -> "Widespread dust"
                12 -> "Widespread sand"
                13 -> "Shallow snow"
                14 -> "Widespread snow"
                15 -> "Showers of snow"
                16 -> "Heavy snow"
                17 -> "Hail"
                18 -> "Thunderstorm"
                19 -> "Thunderstorm with hail"
                20 -> "Light rain"
                21 -> "Moderate rain"
                22 -> "Heavy rain"
                23 -> "Showers of rain"
                24 -> "Light snow showers"
                25 -> "Moderate snow showers"
                26 -> "Heavy snow showers"
                27 -> "Light rain and snow"
                28 -> "Moderate rain and snow"
                29 -> "Heavy rain and snow"
                30 -> "Light sleet"
                31 -> "Moderate sleet"
                32 -> "Heavy sleet"
                33 -> "Freezing rain"
                34 -> "Freezing drizzle"
                35 -> "Light drizzle"
                36 -> "Moderate drizzle"
                37 -> "Heavy drizzle"
                38 -> "Heavy freezing drizzle"
                39 -> "Light snow grains"
                40 -> "Moderate snow grains"
                41 -> "Heavy snow grains"
                42 -> "Light snow pellets"
                43 -> "Moderate snow pellets"
                44 -> "Heavy snow pellets"
                45 -> "Light rain showers with thunder"
                46 -> "Heavy rain showers with thunder"
                47 -> "Light snow showers with thunder"
                48 -> "Heavy snow showers with thunder"
                else -> "Unknown weather condition"
            }
        }

        fun getWeatherIconByCode(weatherCode: Int): Int {
            return when (weatherCode) {
                0 -> R.drawable.sun      // Clear sky
                1 -> R.drawable.sun      // Mainly clear
                2 -> R.drawable.cloud    // Partly cloudy
                3 -> R.drawable.cloudy   // Cloudy
                45 -> R.drawable.storm   // Fog
                48 -> R.drawable.storm   // Depositing rime fog
                51 -> R.drawable.cloudy  // Light drizzle
                53 -> R.drawable.cloudy  // Moderate drizzle
                55 -> R.drawable.cloudy  // Heavy drizzle
                61 -> R.drawable.storm   // Light rain
                63 -> R.drawable.storm   // Moderate rain
                65 -> R.drawable.storm   // Heavy rain
                71 -> R.drawable.storm   // Light snow
                73 -> R.drawable.storm   // Moderate snow
                75 -> R.drawable.storm   // Heavy snow
                77 -> R.drawable.storm   // Snow grains
                80 -> R.drawable.storm   // Showers of rain
                81 -> R.drawable.storm   // Heavy showers of rain
                82 -> R.drawable.storm   // Violent showers of rain
                85 -> R.drawable.storm   // Light snow showers
                86 -> R.drawable.storm   // Heavy snow showers
                95 -> R.drawable.storm   // Thunderstorm
                96 -> R.drawable.storm   // Thunderstorm with light hail
                99 -> R.drawable.storm   // Thunderstorm with heavy hail
                else -> R.drawable.sun    // Default to clear sky if code is unknown
            }
        }

        fun parseForecastResponse(responseBody: String): Forecast {
            val hourlyLimit = 30
            val dailyLimit = 10

            val jsonObject = JSONObject(responseBody)

            // Parse current weather
            val currentObj = jsonObject.getJSONObject("current")
            val current = Current(
                time = currentObj.getString("time"),
                temperature = currentObj.getDouble("temperature_2m"),
                isDay = currentObj.getInt("is_day"),
                weatherCode = currentObj.getInt("weather_code")
            )

            // Parse daily weather
            val dailyObj = jsonObject.getJSONObject("daily")
            val dailyList = ArrayList<DailyWeather>()
            val dailyTimes = dailyObj.getJSONArray("time")
            val dailyCodes = dailyObj.getJSONArray("weather_code")
            val dailyMinTemps = dailyObj.getJSONArray("temperature_2m_min")
            val dailyMaxTemps = dailyObj.getJSONArray("temperature_2m_max")

            for (i in 0 until dailyLimit) {
                dailyList.add(
                    DailyWeather(
                        time = dailyTimes.getString(i),
                        weatherCode = dailyCodes.getInt(i),
                        minTemp = dailyMinTemps.getDouble(i),
                        maxtemp = dailyMaxTemps.getDouble(i)
                    )
                )
            }

            // Parse hourly weather
            val hourlyObj = jsonObject.getJSONObject("hourly")
            val hourlyList = ArrayList<HourlyWeather>()
            val hourlyTimes = hourlyObj.getJSONArray("time")
            val hourlyTemps = hourlyObj.getJSONArray("temperature_2m")
            val hourlyCodes = hourlyObj.getJSONArray("weather_code")

            // Get current time
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            val currentTime = Calendar.getInstance().apply {
                time = formatter.parse(currentObj.getString("time"))
            }

            // Limit hourly forecast to now + 30 hours
            for (i in 0 until hourlyTimes.length()) {
                val hourlyTimeString = hourlyTimes.getString(i)
                val hourlyTime = Calendar.getInstance().apply {
                    time = formatter.parse(hourlyTimeString)
                }

                // Only add hourly data for the next 30 hours from the current time
                val diffInHours = (hourlyTime.timeInMillis - currentTime.timeInMillis) / (1000 * 60 * 60)
                if (diffInHours >= 0 && diffInHours <= hourlyLimit) {
                    hourlyList.add(
                        HourlyWeather(
                            time = hourlyTimeString,
                            temp = hourlyTemps.getDouble(i),
                            weatherCode = hourlyCodes.getInt(i)
                        )
                    )
                }
            }

            // Create the Forecast object
            return Forecast(
                hourly = hourlyList,
                daily = dailyList,
                current = current,
            )
        }

        fun parseLocationResponse(responseBody: String): ArrayList<Location> {
            val locations = ArrayList<Location>()
            val countryCityMap = mutableMapOf<String, MutableSet<String>>() // Map to track cities per country

            try {
                val rootObject = JSONObject(responseBody)
                val resultsArray = rootObject.getJSONArray("results")

                for (i in 0 until resultsArray.length()) {
                    val resultObject = resultsArray.getJSONObject(i)
                    Log.i("parseLocationResponse", resultObject.toString())

                    val name = resultObject.optString("name", null)
                    val country = resultObject.optString("country", "Unknown")

                    // Skip if name is null, empty, "Unknown" or if the city already exists in the current country
                    if (name.isNullOrEmpty() || name == "Unknown" || countryCityMap[country]?.contains(name) == true) {
                        continue
                    }

                    // Add the city name to the set for that country
                    countryCityMap.getOrPut(country) { mutableSetOf() }.add(name)

                    val location = Location(
                        id = resultObject.getInt("id"),
                        name = name,
                        latitude = resultObject.getDouble("latitude"),
                        longitude = resultObject.getDouble("longitude"),
                        elevation = resultObject.optDouble("elevation", 0.0),
                        timezone = resultObject.getString("timezone"),
                        feature_code = resultObject.getString("feature_code"),
                        country_code = resultObject.getString("country_code"),
                        country = country,
                        country_id = resultObject.optInt("country_id", 0),
                        population = resultObject.optInt("population", 0),
                        postcodes = resultObject.optJSONArray("postcodes")?.let { jsonArray ->
                            List(jsonArray.length()) { jsonArray.getString(it) }
                        } ?: emptyList(),
                        admin1 = resultObject.optString("admin1", null),
                        admin2 = resultObject.optString("admin2", null),
                        admin3 = resultObject.optString("admin3", null),
                        admin4 = resultObject.optString("admin4", null),
                        admin1_id = if (resultObject.has("admin1_id")) resultObject.optInt("admin1_id") else null,
                        admin2_id = if (resultObject.has("admin2_id")) resultObject.optInt("admin2_id") else null,
                        admin3_id = if (resultObject.has("admin3_id")) resultObject.optInt("admin3_id") else null,
                        admin4_id = if (resultObject.has("admin4_id")) resultObject.optInt("admin4_id") else null
                    )

                    locations.add(location)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return locations
        }

    }
}
