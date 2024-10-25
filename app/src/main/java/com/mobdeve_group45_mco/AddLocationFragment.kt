package com.mobdeve_group45_mco
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobdeve_group45_mco.R
import com.mobdeve_group45_mco.dailyWeather.DailyAdapter
import com.mobdeve_group45_mco.databinding.FragmentAddLocationBinding
import com.mobdeve_group45_mco.databinding.FragmentSearchBinding
import com.mobdeve_group45_mco.hourlyWeather.HourlyAdapter
import com.mobdeve_group45_mco.post.PostAdapter
import com.mobdeve_group45_mco.searchResults.SearchAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddLocationFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding: FragmentAddLocationBinding

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
        viewBinding = FragmentAddLocationBinding.inflate(inflater,container,false)
        return viewBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.fragmentHomeRvHours.adapter = HourlyAdapter(DataGenerator.hourlyWeatherData())
        viewBinding.fragmentHomeRvHours.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.fragmentHomeRvDays.adapter = DailyAdapter(DataGenerator.dailyWeatherData())
        viewBinding.fragmentHomeRvDays.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentHomeRvPosts.adapter = PostAdapter(DataGenerator.loadPostData())
        viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            viewBinding.fragmentHomeRvPosts.context,
            1
        )
        viewBinding.fragmentHomeRvPosts.addItemDecoration(dividerItemDecoration)
        viewBinding.fragmentHomeRvPosts.layoutManager = LinearLayoutManager(requireContext())
    }
}
