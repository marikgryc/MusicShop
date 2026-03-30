package com.example.musicshop

import android.os.Bundle
import android.view.LayoutInflater
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
    private var currentId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_list)

        recyclerView = findViewById(R.id.instruments_rv)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add_instrument)

        // Початкові дані
        generateInitialData()

        // Налаштування адаптера
        adapter = InstrumentAdapter(instrumentList,
            onItemClicked = { pos ->
                Toast.makeText(this, "Обрано: ${instrumentList[pos].name}", Toast.LENGTH_SHORT).show()
            },
            onItemDeleted = { pos ->
                instrumentList.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                adapter.notifyItemRangeChanged(pos, instrumentList.size)
                Toast.makeText(this, "Видалено", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAdd.setOnClickListener {
            addNewInstrument()
        }
    }

    private fun generateInitialData() {
        instrumentList.add(Instrument(currentId++, "Fender Stratocaster", 1200.0, "Гітара"))
        instrumentList.add(Instrument(currentId++, "Gibson Les Paul", 2500.0, "Гітара"))
        instrumentList.add(Instrument(currentId++, "Yamaha P-45", 450.0, "Синтезатор"))
        instrumentList.add(Instrument(currentId++, "Roland Juno-DS", 800.0, "Синтезатор"))
        instrumentList.add(Instrument(currentId++, "Tama Imperialstar", 950.0, "Ударні"))
        instrumentList.add(Instrument(currentId++, "Zildjian K Custom", 400.0, "Ударні"))
        instrumentList.add(Instrument(currentId++, "Selmer Paris Sax", 3200.0, "Духові"))
        instrumentList.add(Instrument(currentId++, "Yamaha YTR-2330", 600.0, "Духові"))
    }

    private fun addNewInstrument() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_instrument, null)
        val etName = dialogView.findViewById<EditText>(R.id.et_name)
        val etPrice = dialogView.findViewById<EditText>(R.id.et_price)
        val etType = dialogView.findViewById<EditText>(R.id.et_type)

        AlertDialog.Builder(this)
            .setTitle("Додати товар")
            .setView(dialogView)
            .setPositiveButton("Додати") { _, _ ->
                val name = etName.text.toString()
                val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
                val type = etType.text.toString()

                if (name.isNotEmpty()) {
                    val newInstr = Instrument(currentId++, name, price, type)
                    instrumentList.add(newInstr)
                    adapter.notifyItemInserted(instrumentList.size - 1)
                    recyclerView.scrollToPosition(instrumentList.size - 1)
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }
}