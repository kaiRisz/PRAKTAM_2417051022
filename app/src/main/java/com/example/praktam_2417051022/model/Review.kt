package com.example.praktam_2417051022.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("nama") val nama: String,
    @SerializedName("kategori") val kategori: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("image_name") val imageResName: String
)