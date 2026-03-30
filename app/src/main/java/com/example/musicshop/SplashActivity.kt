package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthorization()
        }, 2000)
    }

    private fun checkAuthorization() {
        val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        val isAuthorized = sharedPreferences.getBoolean("isAuthorized", false)

        val intent = if (isAuthorized) {
            Intent(this, MainMenuActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}