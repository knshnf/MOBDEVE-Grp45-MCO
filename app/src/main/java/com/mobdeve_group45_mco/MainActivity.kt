package com.mobdeve_group45_mco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment=HomeFragment()
        val profileFragment=ProfileFragment()
        val searchFragment=SearchFragment()

        setCurrentFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.activity_main_bnv_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.miHome->setCurrentFragment(homeFragment)
                R.id.miSearch->setCurrentFragment(searchFragment)
                R.id.miProfile->setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_main_fl_fragment,fragment)
            commit()
        }
}