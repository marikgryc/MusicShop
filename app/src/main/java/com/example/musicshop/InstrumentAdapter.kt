package com.example.musicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InstrumentAdapter(
    private val instruments: MutableList<Instrument>,
    private val onItemClicked: (Int) -> Unit, // Функція для редагування (звичайний клік)
    private val onItemDeleted: (Int) -> Unit  // Функція для видалення (кнопка або довгий клік)
) : RecyclerView.Adapter<InstrumentAdapter.InstrumentViewHolder>() {

    // Цей клас зберігає посилання на елементи дизайну (TextView, Button) для кожного рядка
    class InstrumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.item_name_tv)
        val typeTv: TextView = view.findViewById(R.id.item_type_tv)
        val priceTv: TextView = view.findViewById(R.id.item_price_tv)
        val deleteBtn: ImageButton = view.findViewById(R.id.item_delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instrument, parent, false)
        return InstrumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstrumentViewHolder, position: Int) {
        val instrument = instruments[position]

        // Заповнюємо дані
        holder.nameTv.text = instrument.name
        holder.typeTv.text = "Тип: ${instrument.type}"
        holder.priceTv.text = "Ціна: $${instrument.price}"

        // 1. Редагування при звичайному натисканні на картку
        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }

        // 2. Видалення при натисканні на смітник
        holder.deleteBtn.setOnClickListener {
            onItemDeleted(position)
        }

        // 3. Видалення при довгому натисканні на саму картку
        holder.itemView.setOnLongClickListener {
            onItemDeleted(position)
            true // true означає, що ми обробили цей клік і системі не треба робити щось ще
        }
    }

    override fun getItemCount(): Int {
        return instruments.size
    }
}