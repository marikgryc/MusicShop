package com.example.musicshop

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryDetailActivity : AppCompatActivity() {

    private val categories = listOf(
        "Струнні" to """
        Бренди: Fender (США), Gibson (США), Ibanez (Японія).
        Історія: Перші електрогітари з'явилися у 1930-х роках.
        Музиканти: Джимі Гендрікс, Ерік Клептон.
    """.trimIndent(),

        "Клавішні" to """
        Бренди: Yamaha (Японія), Roland (Японія), Korg (Японія).
        Історія: Фортепіано витіснило клавесин у XVIII столітті.
        Музиканти: Фредді Мерк'юрі, Рей Чарльз.
    """.trimIndent(),

        "Духові" to """
        Бренди: Selmer (Франція), Yamaha (Японія), Bach (США).
        Історія: Саксофон був винайдений Адольфом Саксом у 1840 році.
        Музиканти: Чарлі Паркер, Майлз Девіс.
    """.trimIndent(),

        "Ударні" to """
        Бренди: Tama (Японія), Pearl (Японія), Ludwig (США).
        Історія: Сучасна ударна установка сформувалася на початку XX століття.
        Музиканти: Джон Бонем, Тревіс Баркер.
    """.trimIndent()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)

        val recyclerView: RecyclerView = findViewById(R.id.categories_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = CategoryAdapter(categories) { description ->
            showCategoryInfo(description)
        }
    }

    private fun showCategoryInfo(description: String) {
        AlertDialog.Builder(this)
            .setTitle("Інформація про категорію")
            .setMessage(description)
            .setPositiveButton("Зрозуміло", null)
            .show()
    }

    // Змінено назву з ViewHolder на CategoryViewHolder для усунення конфлікту
    inner class CategoryAdapter(
        private val data: List<Pair<String, String>>,
        private val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val item = data[position]
            holder.textView.text = item.first
            holder.textView.setTextColor(Color.WHITE)
            holder.itemView.setOnClickListener { onClick(item.second) }
        }

        override fun getItemCount() = data.size
    }
}