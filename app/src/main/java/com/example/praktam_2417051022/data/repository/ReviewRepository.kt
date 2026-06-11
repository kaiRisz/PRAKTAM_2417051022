package com.example.praktam_2417051022.data.repository

import android.content.Context
import com.example.praktam_2417051022.data.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReviewRepository {

    companion object {
        private const val PREFS_NAME = "review_prefs"
        private const val KEY_REVIEWS = "list_reviews"

        private var reviewList = mutableListOf<Review>()
        private var isInitialized = false
    }

    fun initialize(context: Context) {
        if (isInitialized) return

        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(KEY_REVIEWS, null)

        if (json != null) {
            val type = object : TypeToken<MutableList<Review>>() {}.type
            reviewList = gson.fromJson(json, type)
        } else {
            // Data tiruan awal (dummy data) saat aplikasi pertama kali berjalan
            reviewList = mutableListOf(
                Review("1", "AOT (Attack on Titan)", "Anime Action / Dark Fantasy", "Attack on Titan bermula di dunia di mana umat manusia hidup di dalam kota yang dikelilingi oleh tembok raksasa...", "https://images.justwatch.com/poster/305541743/s284/attack-on-titan.webp", 5.0),
                Review("2", "Fate Stay Night: UBW", "Fantasy / Supernatural", "Perang Cawan Suci adalah ritual rahasia di mana tujuh penyihir memanggil roh pahlawan...", "https://m.media-amazon.com/images/M/MV5BMTczNTcxMTYxM15BMl5BanBnXkFtZTgwOTAyNjk0NDE@._V1_.jpg", 4.8),
                Review("3", "Spider-Man: Across the Spider-Verse", "Superhero / Sci-Fi", "Setelah bersatu kembali dengan Gwen Stacy, Miles Morales terlempar ke seluruh Multiverse...", "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ4MDAzOQ@@._V1_.jpg", 4.9)
            )
            saveToPreferences(context)
        }
        isInitialized = true
    }

    private fun saveToPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(reviewList)
        editor.putString(KEY_REVIEWS, json)
        editor.apply()
    }

    fun getAllReviews(context: Context): List<Review> {
        initialize(context)
        return reviewList
    }

    fun getReviewByNama(nama: String): Review? {
        return reviewList.find { it.nama.equals(nama, ignoreCase = true) }
    }

    fun createReview(context: Context, review: Review) {
        initialize(context)
        reviewList.add(review)
        saveToPreferences(context)
    }

    fun updateReview(context: Context, oldNama: String, updatedReview: Review) {
        initialize(context)
        val index = reviewList.indexOfFirst { it.nama.equals(oldNama, ignoreCase = true) }
        if (index != -1) {
            reviewList[index] = updatedReview
            saveToPreferences(context)
        }
    }

    fun deleteReview(context: Context, nama: String): Boolean {
        initialize(context)
        val removed = reviewList.removeAll { it.nama.equals(nama, ignoreCase = true) }
        if (removed) {
            saveToPreferences(context)
        }
        return removed
    }
}