package com.mobdeve_group45_mco

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve_group45_mco.api.ApiCall
import com.mobdeve_group45_mco.dailyWeather.DailyAdapter
import com.mobdeve_group45_mco.databinding.FragmentHomeBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.hourlyWeather.HourlyAdapter
import com.mobdeve_group45_mco.post.PostAdapter
import com.mobdeve_group45_mco.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val postList = ArrayList<Post>()

    // Default coordinates for Manila, Philippines
    private val defaultLatitude = 14.599512
    private val defaultLongitude = 120.984222

    private var currentForecast: Forecast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private fun getCoordinatesFromSharedPreferences(): Pair<Double, Double> {
        // Retrieve the saved JSON string from SharedPreferences
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("ForecastPrefs", Context.MODE_PRIVATE)
        val forecastJson = sharedPreferences.getString("saved_forecast", null)
            ?: return Pair(defaultLatitude, defaultLongitude)

        // If the JSON is null (i.e., no saved forecast), return default coordinates

        // Deserialize the JSON string into a Forecast object
        val forecast = Utils.deserializeForecast(forecastJson)

        // Extract latitude and longitude from the Forecast object (assuming the Forecast has location data)
        val latitude = forecast.location.latitude ?: defaultLatitude
        val longitude = forecast.location.longitude ?: defaultLongitude

        return Pair(latitude, longitude)
    }


    @SuppressLint("SetTextI18n")
    private fun callback(forecast: Forecast) {
        currentForecast = forecast

        // Bind the forecast data to the views
        viewBinding.fragmentHomeTvConditions.text = Utils.getWeatherDescription(forecast.current.weatherCode)
        viewBinding.fragmentHomeRvHours.adapter = HourlyAdapter(forecast.hourly)
        viewBinding.fragmentHomeRvHours.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.fragmentHomeRvDays.adapter = DailyAdapter(forecast.daily)
        viewBinding.fragmentHomeRvDays.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentHomeRvPosts.adapter = PostAdapter(postList)
        viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentHomeTvTemperature.text = "${forecast.current.temperature}°"
        viewBinding.fragmentHomeTvCity.text = forecast.location.name
        val dividerItemDecoration = DividerItemDecoration(viewBinding.fragmentHomeRvPosts.context, LinearLayoutManager.VERTICAL)
        viewBinding.fragmentHomeRvPosts.addItemDecoration(dividerItemDecoration)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve coordinates from SharedPreferences or use default coordinates
        val (latitude, longitude) = getCoordinatesFromSharedPreferences()

        // Make the API call using the retrieved latitude and longitude
        ApiCall().getForecast(requireContext(), { forecast ->
            try {
                db.collection("posts")
                    .get()
                    .addOnSuccessListener { documents ->
                        postList.clear() // Clear existing data before adding new ones
                        for (document in documents) {
                            if (document.getString("city") == forecast.location.name &&
                                document.getString("countryCode") == forecast.location.country_code) {
                                val post = document.toObject(Post::class.java)
                                postList.add(post)
                            }
                        }
                        viewBinding.fragmentHomeRvPosts.adapter?.notifyDataSetChanged()
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FirestoreError", "Error fetching posts: ${exception.message}")
                    }

                callback(forecast)

                // Add listener for Create Post button
                viewBinding.btnCreatePost.setOnClickListener {
                    val intent = Intent(requireContext(), CreatePost::class.java)

                    intent.putExtra("CITY", forecast.location.name)
                    intent.putExtra("COUNTRY_CODE", forecast.location.country_code)

                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CallbackError", "An error occurred in the callback: ${e.message}")
            }
        }, latitude, longitude)  // Pass the coordinates to the API call
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        // Retrieve coordinates from SharedPreferences or use default coordinates
        val (latitude, longitude) = getCoordinatesFromSharedPreferences()

        // Make the API call using the retrieved latitude and longitude
        ApiCall().getForecast(requireContext(), { forecast ->
            try {
                db.collection("posts")
                    .get()
                    .addOnSuccessListener { documents ->
                        postList.clear() // Clear existing data before adding new ones
                        for (document in documents) {
                            if (document.getString("city") == forecast.location.name &&
                                document.getString("countryCode") == forecast.location.country_code) {
                                val post = document.toObject(Post::class.java)
                                postList.add(post)
                            }
                        }
                        viewBinding.fragmentHomeRvPosts.adapter?.notifyDataSetChanged()
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FirestoreError", "Error fetching posts: ${exception.message}")
                    }

                callback(forecast)

                // Add listener for Create Post button
                viewBinding.btnCreatePost.setOnClickListener {
                    val intent = Intent(requireContext(), CreatePost::class.java)

                    intent.putExtra("CITY", forecast.location.name)
                    intent.putExtra("COUNTRY_CODE", forecast.location.country_code)

                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CallbackError", "An error occurred in the callback: ${e.message}")
            }
        }, latitude, longitude)  // Pass the coordinates to the API call
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}