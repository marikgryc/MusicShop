package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.view.View
import androidx.cardview.widget.CardView

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val greetingTv: TextView = findViewById(R.id.greeting_tv)
        val cardCatalog = findViewById<CardView>(R.id.card_catalog)
        val cardCategories = findViewById<CardView>(R.id.card_categories)
        val cardProfile = findViewById<CardView>(R.id.card_profile)
        val cardLogout = findViewById<CardView>(R.id.card_logout)

        // 1. Отримуємо збережені ім'я та прізвище
        val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        val firstName = sharedPreferences.getString("firstName", "Гостю") // "Гостю" - значення за замовчуванням
        val lastName = sharedPreferences.getString("lastName", "")

        // 2. Встановлюємо привітання
        greetingTv.text = "Привіт, $firstName $lastName!"

        // 3. Обробка натискань на кнопки меню
        cardCatalog.setOnClickListener {
            val intent = Intent(this, InstrumentListActivity::class.java)
            startActivity(intent)
        }

        cardCategories.setOnClickListener {
            val intent = Intent(this, CategoryDetailActivity::class.java)
            startActivity(intent)
        }

        cardProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        cardLogout.setOnClickListener {
            // Скидаємо авторизацію згідно з ТЗ [cite: 30]
            val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isAuthorized", false).apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}