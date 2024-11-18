package com.mobdeve_group45_mco.api
import com.squareup.okhttp.ResponseBody
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.forecast.Current
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather
import org.json.JSONObject
import retrofit.*
import java.text.SimpleDateFormat
import java.util.Calendar

class ApiCall {
    fun getForecast(context: Context, callback: (Forecast) -> Unit, longitude: Number, latitude: Number){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.open-meteo.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<JsonObject> = service.getForecast(latitude, longitude)
        Log.i("Enqueue", "Enqueuing")

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(response: Response<JsonObject>?, retrofit: Retrofit?) {
                if (response != null) {
                    Log.i("Api Response", response.isSuccess.toString())
                }
                if(response!!.isSuccess){
                    Log.i("Api Response", response.isSuccess.toString())
                    Log.i("Api Response", response.body().toString())
                    val forecast: Forecast = parseForecastResponse(response.body().toString())
                    Log.i("Api Response", forecast.toString())
                    callback(forecast)
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
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

        // Parse general metadata
        val latitude = jsonObject.getDouble("latitude")
        val longitude = jsonObject.getDouble("longitude")

        // Create the Forecast object
        return Forecast(
            hourly = hourlyList,
            daily = dailyList,
            current = current,
            latitude = latitude,
            longitude = longitude
        )
    }

}