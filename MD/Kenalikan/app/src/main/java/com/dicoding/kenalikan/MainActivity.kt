package com.dicoding.kenalikan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.kenalikan.databinding.ActivityMainBinding
import com.dicoding.kenalikan.fragment.CameraFragment
import com.dicoding.kenalikan.fragment.HomeFragment
import com.dicoding.kenalikan.fragment.RentFragment
import com.dicoding.kenalikan.weatherclass.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

            binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.idProfile -> {

                    true
                }
                R.id.idKeluar -> {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

            bottomNavigationView = findViewById(R.id.bottomNav)
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
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