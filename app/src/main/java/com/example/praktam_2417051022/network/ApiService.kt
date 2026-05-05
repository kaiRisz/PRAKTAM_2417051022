package com.example.praktam_2417051022.network

import com.example.praktam_2417051022.model.Review
import retrofit2.http.GET

interface ApiService {
    @GET("review_anime.json")
    suspend fun getReviews(): List<Review>
}