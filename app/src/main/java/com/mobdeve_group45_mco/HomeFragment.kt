package com.mobdeve_group45_mco

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve_group45_mco.api.ApiCall
import com.mobdeve_group45_mco.dailyWeather.DailyAdapter
import com.mobdeve_group45_mco.databinding.FragmentHomeBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.hourlyWeather.HourlyAdapter
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return viewBinding.getRoot()
    }


    fun callback(forecast: Forecast) {
        viewBinding.fragmentHomeTvConditions.text = Utils.getWeatherDescription(forecast.current.weatherCode)
        viewBinding.fragmentHomeRvHours.adapter = HourlyAdapter(forecast.hourly)
        viewBinding.fragmentHomeRvHours.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.fragmentHomeRvDays.adapter = DailyAdapter(forecast.daily)
        viewBinding.fragmentHomeRvDays.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentHomeRvPosts.adapter = PostAdapter(DataGenerator.loadPostData())
        viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentHomeTvTemperature.text = forecast.current.temperature.toString() + "°"
        val dividerItemDecoration = DividerItemDecoration(
            viewBinding.fragmentHomeRvPosts.context,
            LinearLayoutManager.VERTICAL
        )
        viewBinding.fragmentHomeRvPosts.addItemDecoration(dividerItemDecoration)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ApiCall().getForecast(requireContext(), { forecast ->
            callback(forecast)
        }, 120.9822, 14.6042)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
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