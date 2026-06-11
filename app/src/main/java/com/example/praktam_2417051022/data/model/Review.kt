package com.example.praktam_2417051022.data.model

data class Review(
    val id: String,
    val nama: String,
    val kategori: String,
    val deskripsi: String,
    val imageUrl: String,
    val rating: Double = 5.0
)