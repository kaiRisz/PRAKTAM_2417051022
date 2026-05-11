package com.example.praktam_2417051022.data.repository

import com.example.praktam_2417051022.data.api.RetrofitClient
import com.example.praktam_2417051022.data.model.Review

class FoodRepository {
    suspend fun getFoods(): List<Review> {
        return try {
            RetrofitClient.instance.getReviews()
        } catch (e: Exception) {
            emptyList()
        }
    }
}