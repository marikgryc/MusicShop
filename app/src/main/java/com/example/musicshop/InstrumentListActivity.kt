package com.example.musicshop

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InstrumentListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InstrumentAdapter
    private val instrumentList = mutableListOf<Instrument>()
    private var currentId = 1 // Щоб генерувати унікальні ID для нових товарів

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_list)

        recyclerView = findViewById(R.id.instruments_rv)
        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add_instrument)

        // 1. Генеруємо початкові дані (мінімум 10 штук)
        generateInitialData()

        // 2. Налаштовуємо RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = InstrumentAdapter(
            instruments = instrumentList,
            onItemClicked = { position -> editInstrumentName(position) },
            onItemDeleted = { position -> deleteInstrument(position) }
        )
        recyclerView.adapter = adapter

        // 3. Додавання через FloatingActionButton (+)
        fabAdd.setOnClickListener {
            addNewInstrument()
        }
    }

    private fun generateInitialData() {
        val types = listOf("Гітара", "Синтезатор", "Ударні", "Духові")
        for (i in 1..10) {
            instrumentList.add(Instrument(currentId++, "Інструмент $i", 150.0 + (i * 10), types[i % 4]))
        }
    }

    private fun deleteInstrument(position: Int) {
        val instrumentName = instrumentList[position].name
        instrumentList.removeAt(position)
        adapter.notifyItemRemoved(position)
        // Оновлюємо індекси інших елементів, щоб не було багів при видаленні
        adapter.notifyItemRangeChanged(position, instrumentList.size)

        Toast.makeText(this, "$instrumentName видалено", Toast.LENGTH_SHORT).show()
    }

    private fun editInstrumentName(position: Int) {
        val instrument = instrumentList[position]

        // Створюємо поле для вводу тексту в діалозі
        val editText = EditText(this)
        editText.setText(instrument.name)

        AlertDialog.Builder(this)
            .setTitle("Редагувати назву")
            .setView(editText)
            .setPositiveButton("Зберегти") { _, _ ->
                val newName = editText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    instrument.name = newName
                    adapter.notifyItemChanged(position) // Кажемо адаптеру оновити цей рядок
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun addNewInstrument() {
        val editText = EditText(this)
        editText.hint = "Назва нового інструмента"

        AlertDialog.Builder(this)
            .setTitle("Додати інструмент")
            .setView(editText)
            .setPositiveButton("Додати") { _, _ ->
                val name = editText.text.toString().trim()
                if (name.isNotEmpty()) {
                    val newInstrument = Instrument(currentId++, name, 999.0, "Новинка")
                    instrumentList.add(newInstrument)
                    adapter.notifyItemInserted(instrumentList.size - 1) // Додаємо в кінець
                    recyclerView.scrollToPosition(instrumentList.size - 1) // Прокручуємо список вниз
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }
}