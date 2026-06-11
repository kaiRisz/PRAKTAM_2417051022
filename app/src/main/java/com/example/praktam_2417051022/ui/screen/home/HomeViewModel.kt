package com.example.praktam_2417051022.ui.screen.home

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.data.repository.ReviewRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ReviewRepository()
    private val context = application.applicationContext

    var reviews = mutableStateOf<List<Review>>(listOf())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isError = mutableStateOf(false)
        private set

    init {
        fetchReviews()
    }

    fun fetchReviews() {
        isLoading.value = true
        isError.value = false
        viewModelScope.launch {
            try {
                val data = repository.getAllReviews(context)
                reviews.value = data
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                isError.value = true
            }
        }
    }

    fun deleteReview(nama: String) {
        viewModelScope.launch {
            val isDeleted = repository.deleteReview(context, nama)
            if (isDeleted) {
                reviews.value = repository.getAllReviews(context).toList()
            }
        }
    }
}