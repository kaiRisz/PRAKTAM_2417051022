package com.example.praktam_2417051022.data.network

import com.example.praktam_2417051022.data.model.LoginResponse
import com.example.praktam_2417051022.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body request: Map<String, String>): Response<LoginResponse>

    @POST("users/add")
    suspend fun registerUser(@Body request: Map<String, String>): Response<RegisterResponse>
}