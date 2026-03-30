package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEmailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var continueBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // СПОЧАТКУ ЦЕ

        // ТЕПЕР ШУКАЄМО ЕЛЕМЕНТИ
        val tvGoToRegistration = findViewById<TextView>(R.id.tv_go_to_registration)
        usernameEmailEt = findViewById(R.id.loginUsernameEmail_et)
        passwordEt = findViewById(R.id.loginPassword_et)
        continueBtn = findViewById(R.id.loginContinue_btn)

        tvGoToRegistration.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        continueBtn.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val inputUsernameEmail = usernameEmailEt.text.toString().trim()
        val inputPassword = passwordEt.text.toString().trim()

        if (inputUsernameEmail.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        val savedLogin = sharedPreferences.getString("login", "")
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        val isUserFound = (inputUsernameEmail == savedLogin || inputUsernameEmail == savedEmail)
        val isPasswordCorrect = (inputPassword == savedPassword)

        if (isUserFound && isPasswordCorrect) {
            sharedPreferences.edit().putBoolean("isAuthorized", true).apply()
            Toast.makeText(this, "Успішний вхід!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
        }
    }
}