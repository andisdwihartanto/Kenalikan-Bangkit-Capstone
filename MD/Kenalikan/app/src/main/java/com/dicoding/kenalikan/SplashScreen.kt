package com.dicoding.kenalikan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.kenalikan.weatherclass.WelcomeActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)
    @Suppress("DEPRECATION")
    Handler().postDelayed(Runnable {
        val intent = Intent(
            this@SplashScreen,
            WelcomeActivity::class.java
        )
        startActivity(intent)
        finish()
    }, 2000)
}
}