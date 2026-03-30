package com.example.musicshop

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var dobEt: EditText

    // Лаунчер для Камери
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            profileImage.setImageBitmap(imageBitmap)
        }
    }

    // Лаунчер для Галереї
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileImage.setImageURI(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profile_image)
        nameEt = findViewById(R.id.profile_name_et)
        emailEt = findViewById(R.id.profile_email_et)
        dobEt = findViewById(R.id.profile_dob_et)
        val btnChangePhoto: Button = findViewById(R.id.btn_change_photo)
        val btnSave: Button = findViewById(R.id.btn_save_profile)

        // Завантажуємо поточні дані
        val prefs = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
        nameEt.setText(prefs.getString("firstName", ""))
        emailEt.setText(prefs.getString("email", ""))

        val day = prefs.getInt("dobDay", 1)
        val month = prefs.getInt("dobMonth", 0)
        val year = prefs.getInt("dobYear", 2000)
        dobEt.setText("$day.${month + 1}.$year")

        // Вибір дати
        dobEt.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d ->
                dobEt.setText("$d.${m + 1}.$y")
            }, year, month, day).show()
        }

        // Вибір фото
        btnChangePhoto.setOnClickListener {
            val options = arrayOf("Камера", "Галерея")
            AlertDialog.Builder(this)
                .setTitle("Оберіть джерело")
                .setItems(options) { _, which ->
                    if (which == 0) {
                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        takePictureLauncher.launch(takePictureIntent)
                    } else {
                        pickImageLauncher.launch("image/*")
                    }
                }
                .show()
        }

        // Збереження
        btnSave.setOnClickListener {
            prefs.edit().apply {
                putString("firstName", nameEt.text.toString())
                putString("email", emailEt.text.toString())
                apply()
            }
            Toast.makeText(this, "Дані оновлено!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}