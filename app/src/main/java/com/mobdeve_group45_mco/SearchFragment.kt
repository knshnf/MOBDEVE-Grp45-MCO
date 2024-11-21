package com.mobdeve_group45_mco

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve_group45_mco.api.ApiCall
import com.mobdeve_group45_mco.databinding.FragmentSearchBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.searchResults.SearchAdapter
import android.os.Handler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding: FragmentSearchBinding

    // Handler and Runnable for debouncing
    private val handler = Handler()
    private val debounceRunnable = Runnable {
        val query = viewBinding.fragmentSearchEtSearchfield.text.toString()
        performSearch(query)
    }

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
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentSearchEtSearchfield.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This function is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This function is called while the text is being changed
            }

            override fun afterTextChanged(s: Editable?) {
                // Cancel any previous debounced actions
                handler.removeCallbacks(debounceRunnable)
                // Schedule the new debounce action with a delay
                handler.postDelayed(debounceRunnable, 500)  // 500ms delay
            }
        })
    }

    fun callback(forecasts: ArrayList<Forecast>) {
        Log.i("Search Callback", forecasts.toString())
        viewBinding.fragmentSearchRvResults.adapter = SearchAdapter(forecasts, parentFragmentManager)
        viewBinding.fragmentSearchRvResults.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            viewBinding.fragmentSearchRvResults.context,
            1
        )
        viewBinding.fragmentSearchRvResults.addItemDecoration(dividerItemDecoration)
    }

    fun performSearch(query: String) {
        if (query.isNotEmpty() && query.length > 2) {
            ApiCall().getForecasts(requireContext(), { forecasts ->
                callback(forecasts)
            }, query)
        } else {
            Log.i("Search", "Query is empty, no search performed.")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}