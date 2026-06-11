package com.example.praktam_2417051022.data.model

<<<<<<< HEAD
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("nama") val nama: String,
    @SerializedName("kategori") val kategori: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("image_url") val imageUrl: String
=======
data class Review(
    val id: String,
    val nama: String,
    val kategori: String,
    val deskripsi: String,
    val imageUrl: String,
    val rating: Double
>>>>>>> be73391 (bikin fitur CRUD)
)