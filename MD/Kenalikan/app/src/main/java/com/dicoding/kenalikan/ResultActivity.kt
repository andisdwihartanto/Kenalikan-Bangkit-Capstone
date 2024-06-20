package com.dicoding.kenalikan

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.kenalikan.databinding.ActivityResultBinding
import java.util.concurrent.locks.Condition

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = binding.viewImage
        val fishNameTextView = binding.typeFish
        val fishDescriptionTextView = binding.fishDesc
        val fishPriceTextView = binding.fishPrice

        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        val fishName = intent.getStringExtra(EXTRA_FISH_NAME)
        val fishDescription = intent.getStringExtra(EXTRA_FISH_DESCRIPTION)

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)
        }
        fishNameTextView.text = fishName
        fishDescriptionTextView.text = fishDescription

        when (fishName) {
            "Red Mullet" -> fishPriceTextView.text = "RP 100.000/100gram"
            "Red Sea Bream" -> fishPriceTextView.text = "RP 200.000/100gram"
            "Hourse Mackerel" -> fishPriceTextView.text = "RP 150.000/100gram"
            "Gilt-Head Bream" -> fishPriceTextView.text = "RP 100.000/100gram"
            "Black Sea Sprat" -> fishPriceTextView.text = "RP 200.000/100gram"
            "Trout" -> fishPriceTextView.text = "RP 150.000/100gram"
            "Striped Red Mullet" -> fishPriceTextView.text = "RP 100.000/100gram"
            "Shrimp" -> fishPriceTextView.text = "RP 200.000/100gram"
            "Sea Bass" -> fishPriceTextView.text = "RP 150.000/100gram"
            else -> fishPriceTextView.text = "Price not available"
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
        const val EXTRA_FISH_NAME = "EXTRA_FISH_NAME"
        const val EXTRA_FISH_DESCRIPTION = "EXTRA_FISH_DESCRIPTION"
    }
}