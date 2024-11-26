package com.mobdeve_group45_mco
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve_group45_mco.dailyWeather.DailyAdapter
import com.mobdeve_group45_mco.databinding.FragmentAddLocationBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.hourlyWeather.HourlyAdapter
import com.mobdeve_group45_mco.post.PostAdapter
import com.mobdeve_group45_mco.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddLocationFragment : BottomSheetDialogFragment() {
    private var forecastJson: String? = null
    private lateinit var viewBinding: FragmentAddLocationBinding
    private val db = FirebaseFirestore.getInstance()
    private val postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            forecastJson = it.getString(ARG_FORECAST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddLocationBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db.collection("posts")
            .get()
            .addOnSuccessListener { documents ->
                postList.clear() // Clear existing data before adding new ones
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
                    postList.add(post)
                }
                PostAdapter(postList).notifyDataSetChanged() // Notify adapter of data changes
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error fetching posts: ${exception.message}")
            }

        val forecast = forecastJson?.let { Utils.deserializeForecast(it) }

        forecast?.let {
            viewBinding.fragmentHomeTvConditions.text = Utils.getWeatherDescription(it.current.weatherCode)
            viewBinding.fragmentHomeRvHours.adapter = HourlyAdapter(it.hourly)
            viewBinding.fragmentHomeRvHours.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            viewBinding.fragmentHomeRvDays.adapter = DailyAdapter(it.daily)
            viewBinding.fragmentHomeRvDays.layoutManager = LinearLayoutManager(requireContext())
            viewBinding.fragmentHomeRvPosts.adapter = PostAdapter(postList)
            viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
            viewBinding.fragmentHomeTvTemperature.text = "${it.current.temperature}°"
            viewBinding.fragmentHomeTvCity.text = it.location.name
            val dividerItemDecoration = DividerItemDecoration(
                viewBinding.fragmentHomeRvPosts.context,
                LinearLayoutManager.VERTICAL
            )
            viewBinding.fragmentHomeRvPosts.addItemDecoration(dividerItemDecoration)

            // Handle button click to save Forecast in SharedPreferences
            viewBinding.fragmentAddLocationBtnAdd.setOnClickListener {
                saveForecastToSharedPreferences(forecast)
                dismiss()
            }
        }
    }

    private fun saveForecastToSharedPreferences(forecast: Forecast) {
        // Obtain SharedPreferences
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "ForecastPrefs", Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()

        // Serialize Forecast object to JSON string
        val forecastJson = Utils.serializeForecast(forecast)

        // Save JSON string in SharedPreferences
        editor.putString("saved_forecast", forecastJson)
        editor.apply()

        // Notify the user
        Toast.makeText(requireContext(), "Forecast saved!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_FORECAST = "arg_forecast"

        fun newInstance(forecast: Forecast): AddLocationFragment {
            val fragment = AddLocationFragment()
            val args = Bundle()

            // Serialize the forecast object to JSON
            args.putString(ARG_FORECAST, Utils.serializeForecast(forecast))
            fragment.arguments = args
            return fragment
        }
    }
}

