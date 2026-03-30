package com.example.musicshop

data class Instrument(
    val id: Int, // Додаємо ID для зручності видалення/редагування
    var name: String,
    var price: Double,
    var type: String
)