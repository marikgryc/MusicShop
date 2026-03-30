package com.example.musicshop

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ProfileActivity : AppCompatActivity() {

    // Оголошуємо змінні
    private lateinit var firstNameEt: EditText
    private lateinit var lastNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var dobEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Ініціалізуємо змінні (перевір, щоб ID в XML були такими самими!)
        firstNameEt = findViewById(R.id.profile_first_name_et)
        lastNameEt = findViewById(R.id.profile_last_name_et)
        emailEt = findViewById(R.id.profile_email_et)
        dobEt = findViewById(R.id.profile_dob_et)
        val btnSave = findViewById<Button>(R.id.btn_save_profile)

        // Завантажуємо дані з пам'яті
        val prefs = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        firstNameEt.setText(prefs.getString("firstName", ""))
        lastNameEt.setText(prefs.getString("lastName", ""))
        emailEt.setText(prefs.getString("email", ""))
        dobEt.setText(prefs.getString("dob", ""))

        // Вибір дати
        dobEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                dobEt.setText("$d.${m + 1}.$y")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Збереження
        btnSave.setOnClickListener {
            prefs.edit().apply {
                putString("firstName", firstNameEt.text.toString())
                putString("lastName", lastNameEt.text.toString())
                putString("email", emailEt.text.toString())
                putString("dob", dobEt.text.toString())
                apply()
            }
            Toast.makeText(this, "Дані оновлено!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}