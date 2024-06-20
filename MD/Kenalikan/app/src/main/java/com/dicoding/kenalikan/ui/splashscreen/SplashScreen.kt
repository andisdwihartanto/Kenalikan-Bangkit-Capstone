package com.dicoding.kenalikan.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.data.weatherclass.WelcomeActivity
import com.dicoding.kenalikan.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)
    @Suppress("DEPRECATION")
    Handler().postDelayed(Runnable {
        val intent = Intent(
            this@SplashScreen,
            MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }, 2000)
}
}