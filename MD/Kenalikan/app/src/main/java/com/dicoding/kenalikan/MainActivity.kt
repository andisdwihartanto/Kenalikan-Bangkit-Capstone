package com.dicoding.kenalikan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.kenalikan.fragment.CameraFragment
import com.dicoding.kenalikan.fragment.HomeFragment
import com.dicoding.kenalikan.fragment.RentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.idBeranda -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.idKamera -> {
                    replaceFragment(CameraFragment())
                    true
                }
                R.id.idRental -> {
                    replaceFragment(RentFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
    }
}