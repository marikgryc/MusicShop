package com.example.musicshop

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {

    // Змінні для збереження вибраної дати
    private var selectedDay = 0
    private var selectedMonth = 0
    private var selectedYear = 0

    // Оголошення View елементів
    private lateinit var firstNameEt: EditText
    private lateinit var lastNameEt: EditText
    private lateinit var loginEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var dateOfBirthEt: EditText
    private lateinit var continueBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Ініціалізація View
        firstNameEt = findViewById(R.id.firstName_et)
        lastNameEt = findViewById(R.id.lastName_et)
        loginEt = findViewById(R.id.login_et)
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        confirmPasswordEt = findViewById(R.id.confirmPassword_et)
        dateOfBirthEt = findViewById(R.id.dateOfBirth_et)
        continueBtn = findViewById(R.id.continue_btn)
        // 1. Логіка для Date of birth (DatePickerDialog)
        // Робимо поле натискабельним, але без ручного введення
        dateOfBirthEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Оновлюємо глобальні змінні та текст у полі
                    this.selectedYear = selectedYear
                    this.selectedMonth = selectedMonth
                    this.selectedDay = selectedDayOfMonth
                    // Форматуємо дату (наприклад, dd.mm.yyyy)
                    dateOfBirthEt.setText("$selectedDayOfMonth.${selectedMonth + 1}.$selectedYear")
                    // Прибираємо помилку, якщо вона була
                    dateOfBirthEt.error = null
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        val goToLoginTv: TextView = findViewById(R.id.goToLogin_tv)

        goToLoginTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закриваємо поточний екран
        }

        // 2. Логіка кнопки "Продовжити" (Валідація та Збереження)
        continueBtn.setOnClickListener {
            performRegistration()
        }
    }

    private fun performRegistration() {
        // Отримуємо значення з полів
        val firstName = firstNameEt.text.toString().trim()
        val lastName = lastNameEt.text.toString().trim()
        val login = loginEt.text.toString().trim()
        val email = emailEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val confirmPassword = confirmPasswordEt.text.toString().trim()
        val dateOfBirth = dateOfBirthEt.text.toString().trim()

        // 3. Перевірки (Валідація) згідно ТЗ:

        // Перевірка всіх полів на заповненість
        var isValid = true

        if (firstName.isEmpty()) {
            firstNameEt.error = "Введіть ім'я"
            isValid = false
        }
        if (lastName.isEmpty()) {
            lastNameEt.error = "Введіть прізвище"
            isValid = false
        }
        if (login.isEmpty()) {
            loginEt.error = "Введіть логін"
            isValid = false
        }
        // Email повинен містити символи @ та .
        if (email.isEmpty()) {
            emailEt.error = "Введіть Email"
            isValid = false
        } else if (!email.contains("@") || !email.contains(".")) {
            emailEt.error = "Неправильний формат Email"
            isValid = false
        }
        // Паролі мають бути довжиною від 6 символів.
        if (password.isEmpty()) {
            passwordEt.error = "Введіть пароль"
            isValid = false
        } else if (password.length < 6) {
            passwordEt.error = "Пароль має бути від 6 символів"
            isValid = false
        }
        // Паролі мають збігатися.
        if (confirmPassword.isEmpty()) {
            confirmPasswordEt.error = "Повторіть пароль"
            isValid = false
        } else if (password != confirmPassword) {
            confirmPasswordEt.error = "Паролі не збігаються"
            isValid = false
        }
        if (dateOfBirth.isEmpty()) {
            dateOfBirthEt.error = "Виберіть дату народження"
            isValid = false
        }

        // 4. Успішна валідація — зберігаємо в SharedPreferences
        if (isValid) {
            val sharedPreferences = getSharedPreferences("MusicShopPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Зберігаємо дані окремими ключами
            editor.putString("firstName", firstName)
            editor.putString("lastName", lastName)
            editor.putString("login", login)
            editor.putString("email", email)
            editor.putString("password", password)
            // Також зберігаємо рік, місяць та день окремо, це може знадобитися пізніше
            editor.putInt("dobDay", selectedDay)
            editor.putInt("dobMonth", selectedMonth)
            editor.putInt("dobYear", selectedYear)

            // Важливо: ми не встановлюємо isAuthorized = true, бо це Екран входу робить.
            editor.apply()

            Toast.makeText(this, "Реєстрація успішна!", Toast.LENGTH_SHORT).show()

            // 5. Перехід до LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}