package com.example.praktam_2417051022.ui.screen.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.api.RetrofitClient
import com.example.praktam_2417051022.data.local.AppDatabase
import com.example.praktam_2417051022.data.model.Review
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val reviewDao = AppDatabase.getDatabase(application).reviewDao()

    private val _reviews = mutableStateOf<List<Review>>(emptyList())
    val reviews: State<List<Review>> = _reviews

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    init {
        observeLocalReviews()
        fetchReviewsFromApi()
    }

    private fun observeLocalReviews() {
        viewModelScope.launch {
            reviewDao.getAllReviews().collect { localList ->
                _reviews.value = localList
            }
        }
    }

    fun fetchReviewsFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val apiResponse = RetrofitClient.instance.getReviews()
                apiResponse.forEach { review ->
                    reviewDao.insertReview(review)
                }
            } catch (e: Exception) {
                if (_reviews.value.isEmpty()) {
                    _isError.value = true
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun insertReview(nama: String, kategori: String, deskripsi: String, imageUrl: String) {
        viewModelScope.launch {
            val newReview = Review(nama = nama, kategori = kategori, deskripsi = deskripsi, imageUrl = imageUrl)
            reviewDao.insertReview(newReview)
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch {
            reviewDao.updateReview(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            reviewDao.deleteReview(review)
        }
    }
}