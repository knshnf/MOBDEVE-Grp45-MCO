package com.mobdeve_group45_mco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve_group45_mco.databinding.ActivitySearchBinding

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}