package com.dicoding.kenalikan

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var fishNameTextView: TextView
    private lateinit var fishDescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        imageView = findViewById(R.id.viewImage)
        fishNameTextView = findViewById(R.id.typeFish)
        fishDescriptionTextView = findViewById(R.id.fishDesc)

        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        val fishName = intent.getStringExtra(EXTRA_FISH_NAME)
        val fishDescription = intent.getStringExtra(EXTRA_FISH_DESCRIPTION)

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri) // Load original image into ImageView
        }
        fishNameTextView.text = fishName
        fishDescriptionTextView.text = fishDescription
    }

    companion object {
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
        const val EXTRA_FISH_NAME = "EXTRA_FISH_NAME"
        const val EXTRA_FISH_DESCRIPTION = "EXTRA_FISH_DESCRIPTION"
    }
}