package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEmailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var continueBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEmailEt = findViewById(R.id.loginUsernameEmail_et)
        passwordEt = findViewById(R.id.loginPassword_et)
        continueBtn = findViewById(R.id.loginContinue_btn)

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

        // Отримуємо збережені дані
        val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        val savedLogin = sharedPreferences.getString("login", "")
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        val isUserFound = (inputUsernameEmail == savedLogin || inputUsernameEmail == savedEmail)
        val isPasswordCorrect = (inputPassword == savedPassword)

        if (isUserFound && isPasswordCorrect) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isAuthorized", true)
            editor.apply()

            Toast.makeText(this, "Успішний вхід!", Toast.LENGTH_SHORT).show()

            // TODO: Перехід до Головного меню (MainMenuActivity)
            // val intent = Intent(this, MainMenuActivity::class.java)
            // startActivity(intent)
            // finish()
        } else {

            Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
        }
    }
}