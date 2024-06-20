package com.dicoding.kenalikan.data.rentfeat

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.kenalikan.MapsActivity
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.databinding.ActivityDescBinding

class DescActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvDataIv: ImageView = findViewById(R.id.imageView)
        val tvDataPlace: TextView = findViewById(R.id.tvTittleRent)
        val tvDataDesc: TextView = findViewById(R.id.tvDesc)

        val image = intent.getIntExtra(EXTRA_IMAGE, 0)
        val place = intent.getStringExtra(EXTRA_PLACE)
        val desc = intent.getStringExtra(EXTRA_DESC)

        tvDataIv.setImageResource(image)
        tvDataPlace.text = place
        tvDataDesc.text = desc

        binding.fabMaps.setOnClickListener {
            startMapActivity()
        }
    }
    private fun startMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_PLACE = "extra_place"
        const val EXTRA_DESC = "extra_desc"
    }
}