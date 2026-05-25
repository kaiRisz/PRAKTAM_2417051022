package com.example.praktam_2417051022.model

import androidx.annotation.DrawableRes

data class Review(
    val nama: String,
    val deskripsi: String,
    val kategori: String,
    @DrawableRes val imageRes: Int
)