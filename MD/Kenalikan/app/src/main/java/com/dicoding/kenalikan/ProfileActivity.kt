package com.dicoding.kenalikan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.kenalikan.databinding.ActivityProfileBinding
import com.dicoding.kenalikan.fragment.HomeFragment
import com.dicoding.kenalikan.weatherclass.WelcomeActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userEmail: String

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        if (userEmail.isNotEmpty()) {
            binding.tvName.text = userEmail
        }

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                binding.tvName.text = user.email
            }
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
        binding.btnExit.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}