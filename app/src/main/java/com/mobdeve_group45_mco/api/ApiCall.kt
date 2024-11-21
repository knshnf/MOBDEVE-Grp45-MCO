package com.mobdeve_group45_mco.api
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.JsonObject
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.forecast.Location
import com.mobdeve_group45_mco.utils.Utils.Companion.parseForecastResponse
import com.mobdeve_group45_mco.utils.Utils.Companion.parseLocationResponse
import org.json.JSONObject
import retrofit.*


class ApiCall {
    fun getForecast(
        context: Context,
        callback: (Forecast) -> Unit,
        latitude: Number,
        longitude: Number
    ) {
        val retrofitForecast: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.open-meteo.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val forecastService: ApiService = retrofitForecast.create(ApiService::class.java)
        val forecastCall: Call<JsonObject> = forecastService.getForecast(latitude, longitude)

        Log.i("Enqueue", "Enqueuing Forecast API Call")

        forecastCall.enqueue(object : Callback<JsonObject> {
            override fun onResponse(response: Response<JsonObject>?, retrofit: Retrofit?) {
                if (response != null) {
                    Log.i("Api Response", response.isSuccess.toString())
                }
                if (response!!.isSuccess) {
                    Log.i("Api Response", response.isSuccess.toString())
                    Log.i("Api Response", response.body().toString())

                    // Parse the forecast response
                    val forecast: Forecast = parseForecastResponse(response.body().toString())

                    // Perform reverse geocoding for location details
                    val retrofitReverseGeocoding: Retrofit = Retrofit.Builder()
                        .baseUrl("https://us1.locationiq.com/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val reverseGeocodingService: ApiService = retrofitReverseGeocoding.create(ApiService::class.java)
                    val reverseGeocodingCall: Call<JsonObject> = reverseGeocodingService.reverseGeocode(
                        apiKey = "pk.ef4083daae987465df09eb250a2ad125", // Replace with your actual API key
                        lat = latitude.toString(),
                        lon = longitude.toString()
                    )

                    Log.i("Enqueue", "Enqueuing Reverse Geocoding API Call")

                    reverseGeocodingCall.enqueue(object : Callback<JsonObject> {
                        override fun onResponse(reverseResponse: Response<JsonObject>?, retrofit: Retrofit?) {
                            if (reverseResponse != null) {
                                Log.i("Reverse Geocoding Response", reverseResponse.message())
                                Log.i("Reverse Geocoding Response", reverseResponse.isSuccess.toString())
                            }
                            if (reverseResponse!!.isSuccess) {
                                Log.i("Reverse Geocoding Response", reverseResponse.body().toString())

                                // Parse reverse geocoding response
                                val responseBody = reverseResponse.body().toString()
                                val rootObject = JSONObject(responseBody)

                                // Safely get nested values using optJSONObject and optString
                                val addressObject = rootObject.optJSONObject("address")

                                val location = Location(
                                    id = 0, // ID is not provided in LocationIQ API
                                    name = addressObject?.optString("city", "Unknown") ?: "Unknown",
                                    latitude = rootObject.optString("lat", "0.0").toDouble(),
                                    longitude = rootObject.optString("lon", "0.0").toDouble(),
                                    elevation = 0.0, // Not provided in LocationIQ response
                                    timezone = "", // Not provided in LocationIQ response
                                    feature_code = addressObject?.optString("attraction", "") ?: "",
                                    country_code = addressObject?.optString("country_code", "Unknown") ?: "Unknown",
                                    country = addressObject?.optString("country", "Unknown") ?: "Unknown",
                                    country_id = 0, // Not provided in LocationIQ response
                                    population = 0, // Not provided in LocationIQ response
                                    postcodes = listOf(addressObject?.optString("postcode", "") ?: ""),
                                    admin1 = addressObject?.optString("state", null),
                                    admin2 = addressObject?.optString("city", null),
                                    admin3 = addressObject?.optString("suburb", null),
                                    admin4 = addressObject?.optString("quarter", null),
                                    admin1_id = null,
                                    admin2_id = null,
                                    admin3_id = null,
                                    admin4_id = null
                                )

                                // Attach location to forecast
                                forecast.location = location
                                callback(forecast)
                            }
                        }

                        override fun onFailure(t: Throwable?) {
                            Log.i("Api Call", "Geocoding Request failed")
                        }
                    })
                }
            }

            override fun onFailure(t: Throwable?) {
                Log.i("Api Call", "Forecast Request failed")
            }
        })
    }


    fun getForecasts(context: Context, callback: (ArrayList<Forecast>) -> Unit, name: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiService = retrofit.create(ApiService::class.java)

        // Step 1: Get locations
        val locationCall: Call<JsonObject> = service.getLocations(name)
        Log.i("Enqueue", "Enqueuing location request")

        locationCall.enqueue(object : Callback<JsonObject> {
            override fun onResponse(response: Response<JsonObject>?, retrofit: Retrofit?) {
                if (response != null && response.isSuccess) {
                    Log.i("Api Response", response.body().toString())
                    val locations = parseLocationResponse(response.body().toString())
                    Log.i("Parsed Locations", locations.toString())

                    // Step 2: Fetch forecasts for each location using ApiCall.getForecast
                    val forecasts = ArrayList<Forecast>()
                    var calls = 0

                    // Loop through each location and call getForecast for each
                    locations.forEachIndexed { index, location ->
                        // Delay the next API call by 0.5 second (500 milliseconds)
                        Handler(Looper.getMainLooper()).postDelayed({
                            ApiCall().getForecast(context, { forecast ->
                                forecasts.add(forecast)

                                calls++
                                Log.i("Forecast Call", "Calls: $calls")

                                // Step 3: Return the result when all calls are done
                                if (calls == locations.size) {
                                    Log.i("All Forecasts Retrieved", forecasts.size.toString())
                                    callback(forecasts)
                                }
                            }, location.latitude, location.longitude)
                        }, index * 500L) // Delay increases by 500ms for each subsequent location
                    }
                } else {
                    Log.e("Location Request Error", "Failed to fetch location")
                }
            }

            override fun onFailure(t: Throwable?) {
                Log.e("Location Request Error", "Failed to fetch locations: $t")
                Log.i("Api Call", "Request failed")
            }
        })
    }

}