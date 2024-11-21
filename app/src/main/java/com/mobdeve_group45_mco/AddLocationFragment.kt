package com.mobdeve_group45_mco
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Deserialize the forecastJson string into a Forecast object
        val forecast = forecastJson?.let {
            Utils.deserializeForecast(it)
        }

        // Use the forecast data to populate views
        forecast?.let {
            viewBinding.fragmentHomeTvConditions.text = Utils.getWeatherDescription(forecast.current.weatherCode)
            viewBinding.fragmentHomeRvHours.adapter = HourlyAdapter(forecast.hourly)
            viewBinding.fragmentHomeRvHours.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            viewBinding.fragmentHomeRvDays.adapter = DailyAdapter(forecast.daily)
            viewBinding.fragmentHomeRvDays.layoutManager = LinearLayoutManager(requireContext())
            viewBinding.fragmentHomeRvPosts.adapter = PostAdapter(DataGenerator.loadPostData())
            viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
            viewBinding.fragmentHomeTvTemperature.text = forecast.current.temperature.toString() + "°"
            viewBinding.fragmentHomeTvCity.text = forecast.location.name
            val dividerItemDecoration = DividerItemDecoration(
                viewBinding.fragmentHomeRvPosts.context,
                LinearLayoutManager.VERTICAL
            )
            viewBinding.fragmentHomeRvPosts.addItemDecoration(dividerItemDecoration)
        }
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

