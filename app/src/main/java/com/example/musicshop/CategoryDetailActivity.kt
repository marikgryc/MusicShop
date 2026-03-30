package com.example.musicshop

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryDetailActivity : AppCompatActivity() {

    private val categories = listOf(
        "Струнні" to "Бренди: Fender, Gibson. \nІсторія: Електрогітари з'явилися у 1930-х. \nМузиканти: Джимі Гендрікс.",
        "Клавішні" to "Бренди: Yamaha, Roland. \nІсторія: Фортепіано з'явилося у XVIII ст. \nМузиканти: Фредді Мерк'юрі.",
        "Духові" to "Бренди: Selmer, Bach. \nІсторія: Саксофон винайдено у 1840 році. \nМузиканти: Майлз Девіс.",
        "Ударні" to "Бренди: Tama, Pearl. \nІсторія: Ударна установка виникла на поч. XX ст. \nМузиканти: Джон Бонем."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)

        val recyclerView = findViewById<RecyclerView>(R.id.categories_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CategoryAdapter(categories) { desc ->
            showCategoryInfo(desc)
        }
    }

    private fun showCategoryInfo(description: String) {
        AlertDialog.Builder(this)
            .setTitle("Інформація")
            .setMessage(description)
            .setPositiveButton("ОК", null)
            .show()
    }

    inner class CategoryAdapter(
        private val data: List<Pair<String, String>>,
        private val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {

        inner class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.category_name_tv)
            val icon: ImageView = view.findViewById(R.id.category_icon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            return CatViewHolder(v)
        }

        override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
            val item = data[position]
            holder.title.text = item.first

            // Ставимо різні системні іконки
            val resId = when(position) {
                0 -> android.R.drawable.ic_menu_gallery
                1 -> android.R.drawable.ic_menu_today
                2 -> android.R.drawable.ic_menu_directions
                else -> android.R.drawable.ic_menu_agenda
            }
            holder.icon.setImageResource(resId)

            holder.itemView.setOnClickListener { onClick(item.second) }
        }

        override fun getItemCount() = data.size
    }
}