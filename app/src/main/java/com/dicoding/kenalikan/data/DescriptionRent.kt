package com.dicoding.kenalikan.data

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.databinding.ActivityDescriptionRentBinding
import com.dicoding.kenalikan.maps.MapsActivity

class DescriptionRent : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionRentBinding

    companion object {
        const val EXTRA_GAMBAR = "extra_gambar"
        const val EXTRA_NAMARENT = "extra_namarent"
        const val EXTRA_ISIDESC = "extra_isidesc"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionRentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvDataGambar: ImageView = findViewById(R.id.gambar)
        val tvDataNamaseiyuu: TextView = findViewById(R.id.namarent)
        val tvDataIsidesc: TextView = findViewById(R.id.isidesc)

        val gambar = intent.getIntExtra(EXTRA_GAMBAR, 0)
        val namaseiyuu = intent.getStringExtra(EXTRA_NAMARENT)
        val isidesc = intent.getStringExtra(EXTRA_ISIDESC)

        tvDataGambar.setImageResource(gambar)
        tvDataNamaseiyuu.text = namaseiyuu
        tvDataIsidesc.text = isidesc


        binding.fabMaps.setOnClickListener {
            startMapActivity()
        }


    }

    private fun startMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}