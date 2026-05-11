package com.example.praktam_2417051022.data.api

import com.example.praktam_2417051022.data.model.Review
import retrofit2.http.GET

interface ApiService {
    @GET("review_anime.json")
    suspend fun getReviews(): List<Review>
}