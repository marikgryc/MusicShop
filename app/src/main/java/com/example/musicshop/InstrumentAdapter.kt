package com.example.musicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InstrumentAdapter(
    private val instruments: MutableList<Instrument>,
    private val onItemClicked: (Int) -> Unit,
    private val onItemDeleted: (Int) -> Unit
) : RecyclerView.Adapter<InstrumentAdapter.InstrumentViewHolder>() {

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

        holder.nameTv.text = instrument.name
        holder.typeTv.text = "Тип: ${instrument.type}"
        holder.priceTv.text = "$${instrument.price}"

        holder.itemView.setOnClickListener { onItemClicked(position) }
        holder.deleteBtn.setOnClickListener { onItemDeleted(position) }
    }

    override fun getItemCount(): Int = instruments.size
}