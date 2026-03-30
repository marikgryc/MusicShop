package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val greetingTv: TextView = findViewById(R.id.greeting_tv)
        val btnCatalog: AppCompatButton = findViewById(R.id.btn_catalog)
        val btnCategories: AppCompatButton = findViewById(R.id.btn_categories)
        val btnProfile: AppCompatButton = findViewById(R.id.btn_profile)
        val btnLogout: AppCompatButton = findViewById(R.id.btn_logout)

        // 1. Отримуємо збережені ім'я та прізвище
        val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        val firstName = sharedPreferences.getString("firstName", "Гостю") // "Гостю" - значення за замовчуванням
        val lastName = sharedPreferences.getString("lastName", "")

        // 2. Встановлюємо привітання
        greetingTv.text = "Привіт, $firstName $lastName!"

        // 3. Обробка натискань на кнопки меню
        btnCatalog.setOnClickListener {
            val intent = Intent(this, InstrumentListActivity::class.java)
            startActivity(intent)
        }

        btnCategories.setOnClickListener {
            val intent = Intent(this, CategoryDetailActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // 4. Логіка кнопки "Вихід"
        btnLogout.setOnClickListener {
            // Скидаємо прапорець авторизації (isAuthorized = false)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isAuthorized", false)
            editor.apply()


            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}