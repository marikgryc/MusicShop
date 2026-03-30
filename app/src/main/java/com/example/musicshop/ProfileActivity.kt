package com.example.musicshop

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ProfileActivity : AppCompatActivity() {

    // Оголошення змінних для UI елементів
    private lateinit var firstNameEt: EditText
    private lateinit var lastNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var dobEt: EditText
    private lateinit var profileIv: ImageView
    private lateinit var btnChangePhoto: Button
    private lateinit var btnSave: Button

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 1. Ініціалізація всіх View
        firstNameEt = findViewById(R.id.profile_first_name_et)
        lastNameEt = findViewById(R.id.profile_last_name_et)
        emailEt = findViewById(R.id.profile_email_et)
        dobEt = findViewById(R.id.profile_dob_et)
        profileIv = findViewById(R.id.profile_image_iv)
        btnChangePhoto = findViewById(R.id.btn_change_photo) // Та сама клікабельна кнопка
        btnSave = findViewById(R.id.btn_save_profile)

        // 2. Завантаження даних із SharedPreferences
        val prefs = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        firstNameEt.setText(prefs.getString("firstName", ""))
        lastNameEt.setText(prefs.getString("lastName", ""))
        emailEt.setText(prefs.getString("email", ""))
        dobEt.setText(prefs.getString("dob", ""))

        // Відновлення фото профілю
        val savedImageUri = prefs.getString("profileImageUri", null)
        if (savedImageUri != null) {
            profileIv.setImageURI(Uri.parse(savedImageUri))
        }

        // 3. Клік по кнопці для зміни фото
        btnChangePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT) // Використовуємо Open Document для стабільного доступу
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // 4. Клік по полю дати (Календар)
        dobEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                dobEt.setText("$d.${m + 1}.$y")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 5. Логіка збереження
        btnSave.setOnClickListener {
            val editor = prefs.edit()
            editor.putString("firstName", firstNameEt.text.toString())
            editor.putString("lastName", lastNameEt.text.toString())
            editor.putString("email", emailEt.text.toString())
            editor.putString("dob", dobEt.text.toString())

            // Якщо вибрали нове фото — зберігаємо його шлях
            imageUri?.let {
                editor.putString("profileImageUri", it.toString())
            }

            editor.apply()
            Toast.makeText(this, "Профіль оновлено!", Toast.LENGTH_SHORT).show()
            finish() // Повертаємось у меню
        }
    }

    // Обробка вибору картинки з галереї
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri
                profileIv.setImageURI(uri)

                // Отримуємо постійний дозвіл на читання файлу, щоб він не зник після перезавантаження
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}